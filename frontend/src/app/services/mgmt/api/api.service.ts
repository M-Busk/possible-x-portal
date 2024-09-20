import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApiService extends RestApplicationClient {

  private baseUrl: string = environment.api_url;

  constructor(private http: HttpClient) {
    super(new AngularHttpClientImpl(http, environment.api_url));
  }

}

