/*
 *  Copyright 2024-2025 Dataport. All rights reserved. Developed as part of the POSSIBLE project.
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

import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-loading-animation',
  templateUrl: './loading-animation.component.html',
  styleUrls: ['./loading-animation.component.scss']
})
export class LoadingAnimationComponent {
  @Input() light: boolean = false;

  imageUrl: string = 'assets/images/brand/spade-white.png';
  imageLightUrl: string = 'assets/images/brand/spade-white.png';

  color: string = 'primary';
  colorLight: string = 'light';

  getColor(): string {
    return this.light ? this.colorLight : this.color;
  }

  getImageUrl(): string {
    return this.light ? this.imageLightUrl : this.imageUrl;
  }
}
