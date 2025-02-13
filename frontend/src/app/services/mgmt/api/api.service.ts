import {Injectable} from '@angular/core';
import {RestApplicationClient} from "./backend";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AngularHttpClientImpl} from "./angular-http-client";

@Injectable({
  providedIn: 'root'
})
export class ApiService extends RestApplicationClient {

  private readonly baseUrl: string = environment.api_url;

  constructor(private readonly http: HttpClient) {
    super(new AngularHttpClientImpl(http, environment.api_url));
  }

}

