import {Component} from '@angular/core';
import {environment} from "../../../environments/environment";

@Component({
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  protected readonly environment = environment;
}
