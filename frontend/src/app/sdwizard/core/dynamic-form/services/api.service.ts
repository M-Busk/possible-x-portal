import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import Data from '../mock/form.mock.json';
import {TranslateService} from "@ngx-translate/core";

@Injectable({
  providedIn: 'root'
})

export class ApiService {
  constructor(public translate: TranslateService) {
  }

  getFieldContraints(acceptType: string = 'application/json'): Observable<any> {
    //const httpOptions = {
    //  headers: new HttpHeaders({
    //    'Access-Control-Allow-Origin': '*',
    //    'Content-Type': acceptType
    //  })
    //};
    return undefined;
    //return this.httpClient.get<any>(`${environment.wizard_api_url}/demo`);
  }

  // Can be used for development
  getFieldsFromFile() {
    return Data;
  }

  upload(file: File): Observable<any> {
    //const apiUrl = Utils.controlUrl(environment.wizard_api_url);
    //const data = new FormData();
    //data.append('file', file);
    return undefined;
    //return this.httpClient.post(`${apiUrl}/convertFile`, data);
  }

  getFiles(): Observable<any> {
    //const apiUrl = Utils.controlUrl(environment.wizard_api_url);
    return undefined;
    //return this.httpClient.get(`${apiUrl}/getAvailableShapes`);
  }

  getFilesCategorized(system: any): Observable<any> {
    //const apiUrl = Utils.controlUrl(environment.wizard_api_url);
    //return this.httpClient.get(`${apiUrl}/getAvailableShapesCategorized?ecoSystem=`+system);
    return undefined;
    //return this.serviceofferingsApiService.fetchAvailableShapes(system);
  }

  getJSON(name: string): Observable<any> {
    //const apiUrl = Utils.controlUrl(environment.wizard_api_url);
    //const params = new HttpParams().set('name', name);
    //incase of choosing language through the link .set('lan',this.translate.currentLang)

    //return this.httpClient.get(`${apiUrl}/getJSON`, {params});
    return undefined;
    //return this.serviceofferingsApiService.fetchShape(name);
  }
}
