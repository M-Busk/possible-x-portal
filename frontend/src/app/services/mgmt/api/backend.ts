/* tslint:disable */

/* eslint-disable */

export interface IParticipantRegistrationRestApi {
}

export interface IParticipantShapeRestApi {
  gxLegalParticipantShape: string;
  gxLegalRegistrationNumberShape: string;
}

export interface IRegistrationRequestTO {
  participantCs: any;
  registrationNumberCs: any;
}

export interface IRegistrationRequestTOBuilder {
}

export interface HttpClient {

  request<R>(requestConfig: {
    method: string;
    url: string;
    queryParams?: any;
    data?: any;
    copyFn?: (data: R) => R;
  }): RestResponse<R>;
}

export class RestApplicationClient {

  constructor(protected httpClient: HttpClient) {
  }

  /**
   * HTTP POST /registration/request
   * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.registerParticipant
   */
  registerParticipant(request: IRegistrationRequestTO): RestResponse<void> {
    return this.httpClient.request({method: "POST", url: uriEncoding`registration/request`, data: request});
  }

  /**
   * HTTP GET /shapes/gx/legalparticipant
   * Java method: eu.possiblex.portal.application.boundary.ParticipantShapeRestApiImpl.getGxLegalParticipantShape
   */
  getGxLegalParticipantShape(): RestResponse<string> {
    return this.httpClient.request({method: "GET", url: uriEncoding`shapes/gx/legalparticipant`});
  }

  /**
   * HTTP GET /shapes/gx/legalregistrationnumber
   * Java method: eu.possiblex.portal.application.boundary.ParticipantShapeRestApiImpl.getGxLegalRegistrationNumberShape
   */
  getGxLegalRegistrationNumberShape(): RestResponse<string> {
    return this.httpClient.request({method: "GET", url: uriEncoding`shapes/gx/legalregistrationnumber`});
  }
}

export type RestResponse<R> = Promise<R>;

function uriEncoding(template: TemplateStringsArray, ...substitutions: any[]): string {
  let result = "";
  for (let i = 0; i < substitutions.length; i++) {
    result += template[i];
    result += encodeURIComponent(substitutions[i]);
  }
  result += template[template.length - 1];
  return result;
}
