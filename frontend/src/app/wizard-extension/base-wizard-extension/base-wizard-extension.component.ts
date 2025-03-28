/*
 *  Copyright 2024 Dataport. All rights reserved. Developed as part of the MERLOT project.
 *  Copyright 2024-2025 Dataport. All rights reserved. Extended as part of the POSSIBLE project.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import {ChangeDetectorRef, Component, ViewChild} from '@angular/core';
import {ShaclFile} from '@models/shacl-file';
import {Shape} from '@models/shape';
import {FormfieldControlService} from '@services/form-field.service';
import {DynamicFormComponent} from '../../sdwizard/core/dynamic-form/dynamic-form.component';
import {ExpandedFieldsComponent} from '@components/expanded-fields/expanded-fields.component';
import {AbstractControl} from '@angular/forms';
import {DynamicFormInputComponent} from '@components/dynamic-form-input/dynamic-form-input.component';
import {DynamicFormArrayComponent} from '@components/dynamic-form-array/dynamic-form-array.component';
import {BehaviorSubject, takeWhile} from 'rxjs';
import {ExportService} from '@services/export.service';
import {Mutex} from 'async-mutex';

@Component({
  selector: 'app-base-wizard-extension',
  templateUrl: './base-wizard-extension.component.html',
  styleUrls: ['./base-wizard-extension.component.scss']
})
export class BaseWizardExtensionComponent {
  public orgaIdFields: AbstractControl[] = [];
  prefillDone: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  protected shaclFile: ShaclFile;
  protected filteredShapes: Shape[];
  protected wizardVisible: boolean = false;
  @ViewChild("wizard") private readonly wizard: DynamicFormComponent;
  private readonly shapeInitialized: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private readonly wizardMutex: Mutex = new Mutex();

  private disabledFields: string[] = [];
  private readonly createDateTimer: NodeJS.Timeout = undefined;

  constructor(protected formFieldService: FormfieldControlService,
              protected exportService: ExportService,
              protected changeDetectorRef: ChangeDetectorRef) {
  }

  public async loadShape(shapeSource: Promise<any>, id: string): Promise<void> {
    await this.wizardMutex.runExclusive(async () => {
      this.reinitWizard();
      let shape = await shapeSource;
      this.selectShape(this.formFieldService.readShaclFile(shape), id);
      this.shapeInitialized.next(true);
    });
  }

  public isShapeLoaded(): boolean {
    return this.shapeInitialized.value;
  }

  public prefillFields(selfDescriptionFields: any, disabledFields: string[]) {
    console.log("prefillFields", selfDescriptionFields)
    if (this.createDateTimer) {
      clearInterval(this.createDateTimer);
    }
    // prefill self-description
    this.disabledFields = disabledFields;
    this.prefillWaitForShape(selfDescriptionFields);
  }

  public generateJsonCs(): any {
    this.wizard.shape.userPrefix = this.wizard.form.get('user_prefix').value;
    this.wizard.shape.downloadFormat = this.wizard.form.get('download_format').value;
    this.wizard.shape.fields = this.wizard.updateFormFieldsValues(this.wizard.formFields, this.wizard.form);
    this.wizard.shape.fields = this.wizard.emptyChildrenFields(this.wizard.shape.fields);
    let jsonCs = this.exportService.saveFile(this.wizard.file);

    jsonCs["id"] = jsonCs["@id"]
    delete jsonCs["@id"]
    //jsonCs["type"] = jsonCs["@type"]
    //delete jsonCs["@type"]
    delete jsonCs[""]

    return jsonCs;
  }

  public ngOnDestroy() {
    if (this.wizard) {
      this.wizard.ngOnDestroy();
    }
  }

  public isWizardFormInvalid() {
    return this.wizard?.form.invalid;
  }

  public isWizardShapePresent(): boolean {
    return !!this.wizard?.shape;
  }

  public setCredentialId(id: string): void {
    let didField = this.wizard.form.get("user_prefix");
    didField.patchValue(id);
  }

  private selectShape(shaclFile: ShaclFile, credentialSubjectId: string): void {
    this.shaclFile = shaclFile;
    this.filteredShapes = this.formFieldService.updateFilteredShapes(this.shaclFile);
    console.log(shaclFile);
    console.log(this.filteredShapes);
    if (this.filteredShapes.length > 1) {
      console.log("too many shapes selected");
    } else {
      // add a field containing the id to avoid creating a new offering
      this.filteredShapes[0].fields.push({
        id: 'user_prefix',
        value: credentialSubjectId,
        key: '',
        name: '',
        datatype: {
          prefix: '',
          value: ''
        },
        required: false,
        minCount: 0,
        maxCount: 0,
        order: 0,
        group: '',
        controlTypes: [],
        in: [],
        or: [],
        validations: [],
        componentType: '',
        childrenFields: [],
        childrenSchema: '',
        prefix: '',
        values: [],
        description: '',
        selfLoop: false
      });
      console.table(this.shaclFile);
      this.updateSelectedShape();
    }
  }

  private updateSelectedShape(): void {
    const shape = this.filteredShapes[0];
    if (shape !== undefined) {
      this.shaclFile.shapes.find(x => x.name === shape.name).selected = true;
    }
  }

  private reinitWizard(): void {
    this.wizardVisible = false;
    this.changeDetectorRef.detectChanges();
    this.shaclFile = undefined;
    this.shapeInitialized.next(false);
    this.prefillDone.next(false);
    this.wizardVisible = true;
    this.changeDetectorRef.detectChanges();
  }

  private prefillWaitForWizard(selfDescriptionFields: any) {
    this.wizard.finishedLoading
      .pipe(takeWhile(finishedLoading => !finishedLoading, true)) // subscribe until finishedLoading is true for first time (inclusive), then unsubscribe
      .subscribe(finishedLoading => {
        if (!finishedLoading) {
          console.log("Wizard not yet ready, waiting for init.");
          return;
        }

        console.log("Wizard initialized");
        console.log("start prefilling fields");

        this.wizardMutex.runExclusive(() => {
          for (let expandedField of this.wizard.expandedFieldsViewChildren) {
            this.processExpandedField(expandedField, selfDescriptionFields);
          }
          for (let formInput of this.wizard.formInputViewChildren) {
            this.processFormInput(formInput, selfDescriptionFields);
          }
          for (let formArray of this.wizard.formArrayViewChildren) {
            this.processFormArray(formArray, selfDescriptionFields);
          }
          this.prefillDone.next(true);
        });
      });
  }

  private prefillWaitForShape(selfDescriptionFields: any) {
    this.shapeInitialized
      .pipe(takeWhile(shapeInitialized => !shapeInitialized, true)) // subscribe until shapeInitialized is true for first time (inclusive), then unsubscribe
      .subscribe(shapeInitialized => {
        if (!shapeInitialized) {
          console.log("Shape not yet initialized, waiting for init.");
          return;
        }

        console.log("Shape initialized");
        this.prefillWaitForWizard(selfDescriptionFields);
      });
  }

  private processFormArray(formArray: DynamicFormArrayComponent, prefillFields: any) {
    if (formArray === undefined || prefillFields === undefined) {
      return;
    }

    let parentKey = formArray.input.prefix + ":" + formArray.input.key;

    // create more inputs for each prefill field after the first one
    if (Object.keys(prefillFields).includes(parentKey)) {
      // form arrays always start with one entry (even if not required), hence choose the max of 1 and the minCount
      for (let i = Math.max(1, formArray.input.minCount); i < prefillFields[parentKey].length; i++) {
        formArray.addInput();
      }
    }

    if (this.disabledFields.includes(parentKey)) {
      formArray.displayAddButton = false;
    }

    let i = 0;
    for (let control of formArray.inputs.controls) {
      if (Object.keys(prefillFields).includes(parentKey)) {
        control.patchValue(this.unpackValueFromField(prefillFields[parentKey][i]));
      }
      if (this.disabledFields.includes(parentKey)) {
        control.disable();
      }
      i += 1;
    }
  }

  private processFormInput(formInput: DynamicFormInputComponent, prefillFields: any) {
    if (formInput === undefined || prefillFields === undefined) {
      return;
    }

    let fullKey = formInput.input.prefix + ":" + formInput.input.key;

    if (fullKey === "gx:providedBy") {
      this.orgaIdFields.push(formInput.form.controls[formInput.input.id]); // save for later reference
    }

    if (Object.keys(prefillFields).includes(fullKey)) {
      let fieldValue = this.unpackValueFromField(prefillFields[fullKey]);
      formInput.form.controls[formInput.input.id].patchValue(fieldValue);
    }
    if (this.disabledFields.includes(fullKey)) {
      formInput.form.controls[formInput.input.id].disable();
    }
  }

  private processExpandedField(expandedField: ExpandedFieldsComponent, prefillFields: any) {
    if (expandedField === undefined || prefillFields === undefined) {
      return;
    }

    let parentKey = expandedField.input.prefix + ":" + expandedField.input.key;
    // create more inputs for each prefill field after the first one
    let updatedInput = false;
    if (Object.keys(prefillFields).includes(parentKey)) {
      for (let i = expandedField.inputs.length; i < prefillFields[parentKey].length; i++) {
        expandedField.addInput();
        updatedInput = true;
      }
      for (let i = expandedField.inputs.length; i > prefillFields[parentKey].length; i--) {
        expandedField.deleteInput(-1);
        updatedInput = true;
      }
    }

    // if we created new inputs, wait for changes
    if (updatedInput) {
      let formInputSub = expandedField.formInputViewChildren.changes.subscribe(_ => {
        this.processExpandedFieldChildrenFields(expandedField, prefillFields);
        formInputSub.unsubscribe();
      });
      let expandedFieldSub = expandedField.expandedFieldsViewChildren.changes.subscribe(_ => {
        this.processExpandedFieldChildrenFields(expandedField, prefillFields);
        expandedFieldSub.unsubscribe();
      });
      let formArraySub = expandedField.formArrayViewChildren.changes.subscribe(_ => {
        this.processExpandedFieldChildrenFields(expandedField, prefillFields);
        formArraySub.unsubscribe();
      });
    } else { //otherwise just start immediately
      this.processExpandedFieldChildrenFields(expandedField, prefillFields);
    }
  }

  private processExpandedFieldChildrenFields(expandedField: ExpandedFieldsComponent, prefillFields: any) {
    let parentKey = expandedField.input.prefix + ":" + expandedField.input.key;

    // since we are always working with a list of inputs, we need to adapt to that in the prefill as well (even if it is just one element)
    if (Object.keys(prefillFields).includes(parentKey)) { // key is in prefill fields
      if (!(prefillFields[parentKey] instanceof Array)) { // field is not array
        prefillFields[parentKey] = [prefillFields[parentKey]]; // set field to array
      }
    } else { // key is not in prefill fields
      prefillFields[parentKey] = []; // init with empty array
    }

    // fill up array up to the expected count of inputs
    for (let i = 0; i < (expandedField.inputs.length - prefillFields[parentKey].length); i++) {
      prefillFields[parentKey].push({});
    }

    let i = 0;
    for (let input of expandedField.inputs) {
      for (let cf of input.childrenFields) {
        this.processFormInput(expandedField.formInputViewChildren.find(f => f.input.id === cf.id), prefillFields[parentKey][i]);
        this.processExpandedField(expandedField.expandedFieldsViewChildren.find(f => f.input.id === cf.id), prefillFields[parentKey][i]);
        this.processFormArray(expandedField.formArrayViewChildren.find(f => f.input.id === cf.id), prefillFields[parentKey][i]);
      }

      i += 1;
    }
  }

  private unpackValueFromField(field) {
    if (field instanceof Array) {
      let unpackedArray = []
      for (let f of field) {
        unpackedArray.push(this.unpackValueFromField(f));
      }
      return unpackedArray;
    } else if (!(field instanceof Object)) {
      return field;
    } else if ("@value" in field) {
      // patch 0 fields to be actually filled since they are regarded as null...
      if (field["@value"] === 0) {
        return "0";
      }
      return field["@value"]
    } else if ("@id" in field) {
      return field["@id"]
    } else if ("id" in field) {
      return field["id"]
    }
  }

}
