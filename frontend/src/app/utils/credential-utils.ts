import { IPojoCredentialSubject } from "../services/mgmt/api/backend";

export function isGxLegalParticipantCs(cs: IPojoCredentialSubject): boolean {
  return cs?.["@type"] === "gx:LegalParticipant";
}

export function isGxLegalRegistrationNumberCs(cs: IPojoCredentialSubject): boolean {
  return cs?.["@type"] === "gx:legalRegistrationNumber";
}
