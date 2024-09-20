// TODO add java classes

//import { IPojoCredentialSubject } from "../services/mgmt/api/backend";

export function isGxLegalParticipantCs(cs: any): boolean {
  return cs?.["@type"] === "gx:LegalParticipant";
}

export function isGxLegalRegistrationNumberCs(cs: any): boolean {
  return cs?.["@type"] === "gx:LegalRegistrationNumber";
}
