/* tslint:disable */
/* eslint-disable */

export interface IParticipantRegistrationRestApi {
    allRegistrationRequests: IRegistrationRequestEntryTO[];
}

export interface IParticipantShapeRestApi {
    gxLegalParticipantShape: string;
    gxLegalRegistrationNumberShape: string;
    pxParticipantExtension: string;
}

export interface IAddressTO {
    countryCode: string;
    countrySubdivisionCode: string;
    streetAddress: string;
    locality: string;
    postalCode: string;
}

export interface IAddressTOBuilder {
}

export interface ICreateRegistrationRequestTO {
    participantCs: IGxLegalParticipantCredentialSubject;
    registrationNumberCs: IGxLegalRegistrationNumberCredentialSubject;
    participantExtensionCs: IPxParticipantExtensionCredentialSubject;
}

export interface ICreateRegistrationRequestTOBuilder {
}

export interface IParticipantDidDataTO {
    did: string;
}

export interface IParticipantDidDataTOBuilder {
}

export interface IRegistrationNumberTO {
    eori: string;
    vatID: string;
    leiCode: string;
}

export interface IRegistrationNumberTOBuilder {
}

export interface IRegistrationRequestEntryTO {
    legalRegistrationNumber: IRegistrationNumberTO;
    legalAddress: IAddressTO;
    headquarterAddress: IAddressTO;
    name: string;
    description: string;
    status: IRequestStatus;
    omejdnConnectorCertificate: IOmejdnConnectorCertificateDto;
    vpLink: string;
    didData: IParticipantDidDataTO;
    emailAddress: string;
}

export interface IRegistrationRequestEntryTOBuilder {
}

export interface IPojoCredentialSubject {
    "@type": "UnknownCredentialSubject" | "gx:LegalParticipant" | "gx:legalRegistrationNumber" | "px:PossibleXLegalParticipantExtension";
    id: string;
}

export interface IUnknownCredentialSubject extends IPojoCredentialSubject {
    "@type": "UnknownCredentialSubject";
}

export interface IGxVcard {
    "gx:countryCode": string;
    "gx:countrySubdivisionCode": string;
    "vcard:street-address": string;
    "vcard:locality": string;
    "vcard:postal-code": string;
}

export interface INodeKindIRITypeId {
    id: string;
}

export interface IGxLegalParticipantCredentialSubject extends IPojoCredentialSubject {
    "@type": "gx:LegalParticipant";
    "gx:legalRegistrationNumber": INodeKindIRITypeId[];
    "gx:legalAddress": IGxVcard;
    "gx:headquarterAddress": IGxVcard;
    "gx:name": string;
    "gx:description": string;
    "@context": { [index: string]: string };
    type: string;
}

export interface IGxLegalRegistrationNumberCredentialSubject extends IPojoCredentialSubject {
    "@type": "gx:legalRegistrationNumber";
    "gx:EORI": string;
    "gx:vatID": string;
    "gx:leiCode": string;
    "@context": { [index: string]: string };
    type: string;
}

export interface IPxParticipantExtensionCredentialSubject extends IPojoCredentialSubject {
    "@type": "px:PossibleXLegalParticipantExtension";
    "px:mailAddress": string;
    "@context": { [index: string]: string };
    type: string;
}

export interface IIntegerDeserializer extends IStdDeserializer<number> {
    /**
     * @deprecated
     */
    nullValue: number;
}

export interface IIntegerSerializer extends IStdSerializer<number> {
}

export interface IStringDeserializer extends IStdDeserializer<string> {
    /**
     * @deprecated
     */
    nullValue: string;
}

export interface IStringSerializer extends IStdSerializer<string> {
}

export interface IUriDeserializer extends IStdDeserializer<string> {
    /**
     * @deprecated
     */
    nullValue: string;
}

export interface IUriSerializer extends IStdSerializer<string> {
}

export interface IOmejdnConnectorCertificateDto {
    client_name: string;
    client_id: string;
    keystore: string;
    password: string;
    scope: string;
}

export interface IClass<T> extends ISerializable, IGenericDeclaration, IType, IAnnotatedElement, IOfField<IClass<any>>, IConstable {
}

export interface IValueInstantiator {
    valueClass: IClass<any>;
    arrayDelegateCreator: IAnnotatedWithParams;
    delegateCreator: IAnnotatedWithParams;
    withArgsCreator: IAnnotatedWithParams;
    valueTypeDesc: string;
    defaultCreator: IAnnotatedWithParams;
}

export interface IJavaType extends IResolvedType, ISerializable, IType {
    javaLangObject: boolean;
    recordType: boolean;
    typeHandler: any;
    valueHandler: any;
    enumImplType: boolean;
    referencedType: IJavaType;
    contentValueHandler: any;
    contentTypeHandler: any;
    erasedSignature: string;
    superClass: IJavaType;
    keyType: IJavaType;
    interfaces: IJavaType[];
    genericSignature: string;
    contentType: IJavaType;
    bindings: ITypeBindings;
}

export interface IJsonDeserializer<T> extends INullValueProvider {
    /**
     * @deprecated
     */
    emptyValue: any;
    /**
     * @deprecated
     */
    nullValue: T;
    emptyAccessPattern: IAccessPattern;
    delegatee: IJsonDeserializer<any>;
    knownPropertyNames: any[];
    objectIdReader: IObjectIdReader;
    cachable: boolean;
}

export interface IObjectIdReader extends ISerializable {
    propertyName: IPropertyName;
    generator: IObjectIdGenerator<any>;
    resolver: IObjectIdResolver;
    idProperty: ISettableBeanProperty;
    deserializer: IJsonDeserializer<any>;
    idType: IJavaType;
}

export interface IJsonSerializer<T> extends IJsonFormatVisitable {
    unwrappingSerializer: boolean;
    delegatee: IJsonSerializer<any>;
}

export interface ISerializable {
}

export interface IGenericDeclaration extends IAnnotatedElement {
    typeParameters: ITypeVariable<any>[];
}

export interface IType {
    typeName: string;
}

export interface IAnnotatedElement {
    annotations: IAnnotation[];
    declaredAnnotations: IAnnotation[];
}

export interface IConstable {
}

export interface IAnnotatedWithParams extends IAnnotatedMember {
    annotationCount: number;
    parameterCount: number;
}

export interface ITypeBindings extends ISerializable {
    empty: boolean;
    typeParameters: IJavaType[];
}

export interface IResolvedType {
    containerType: boolean;
    arrayType: boolean;
    concrete: boolean;
    enumType: boolean;
    collectionLikeType: boolean;
    mapLikeType: boolean;
    referencedType: IResolvedType;
    /**
     * @deprecated
     */
    parameterSource: IClass<any>;
    rawClass: IClass<any>;
    throwable: boolean;
    keyType: IResolvedType;
    interface: boolean;
    primitive: boolean;
    final: boolean;
    abstract: boolean;
    referenceType: boolean;
    contentType: IResolvedType;
}

export interface INullValueProvider {
    nullAccessPattern: IAccessPattern;
}

export interface IPropertyName extends ISerializable {
    namespace: string;
    empty: boolean;
    simpleName: string;
}

export interface IObjectIdGenerator<T> extends ISerializable {
    scope: IClass<any>;
}

export interface IObjectIdResolver {
}

export interface ISettableBeanProperty extends IConcreteBeanPropertyBase, ISerializable {
    ignorable: boolean;
    managedReferenceName: string;
    valueTypeDeserializer: ITypeDeserializer;
    nullValueProvider: INullValueProvider;
    propertyIndex: number;
    injectableValueId: any;
    injectionOnly: boolean;
    valueDeserializer: IJsonDeserializer<any>;
    creatorIndex: number;
    objectIdInfo: IObjectIdInfo;
}

export interface IStdDeserializer<T> extends IJsonDeserializer<T>, ISerializable, IGettable {
    /**
     * @deprecated
     */
    valueClass: IClass<any>;
    valueType: IJavaType;
}

export interface IJsonFormatVisitable {
}

export interface IStdSerializer<T> extends IJsonSerializer<T>, IJsonFormatVisitable, ISchemaAware, ISerializable {
}

export interface ITypeVariable<D> extends IType, IAnnotatedElement {
    name: string;
    bounds: IType[];
    genericDeclaration: D;
    annotatedBounds: IAnnotatedType[];
}

export interface IAnnotation {
}

export interface IOfField<F> extends ITypeDescriptor {
    array: boolean;
    primitive: boolean;
}

export interface ITypeResolutionContext {
}

export interface IAnnotationMap extends IAnnotations {
}

export interface IMember {
    name: string;
    modifiers: number;
    synthetic: boolean;
    declaringClass: IClass<any>;
}

export interface IAnnotatedMember extends IAnnotated, ISerializable {
    /**
     * @deprecated
     */
    typeContext: ITypeResolutionContext;
    allAnnotations: IAnnotationMap;
    member: IMember;
    declaringClass: IClass<any>;
    fullName: string;
}

export interface ITypeDeserializer {
    typeIdResolver: ITypeIdResolver;
    defaultImpl: IClass<any>;
    typeInclusion: IAs;
    propertyName: string;
}

export interface IObjectIdInfo {
    generatorType: IClass<IObjectIdGenerator<any>>;
    resolverType: IClass<IObjectIdResolver>;
    alwaysAsId: boolean;
    scope: IClass<any>;
    propertyName: IPropertyName;
}

export interface IPropertyMetadata extends ISerializable {
    valueNulls: INulls;
    contentNulls: INulls;
    mergeInfo: IMergeInfo;
    required: boolean;
    defaultValue: string;
    index: number;
    description: string;
}

export interface IConcreteBeanPropertyBase extends IBeanProperty, ISerializable {
}

export interface IGettable {
    valueInstantiator: IValueInstantiator;
}

export interface ISchemaAware {
}

export interface IAnnotatedType extends IAnnotatedElement {
    annotatedOwnerType: IAnnotatedType;
    type: IType;
}

export interface ITypeDescriptor {
}

export interface IAnnotations {
}

export interface IAnnotated {
    annotated: IAnnotatedElement;
    name: string;
    public: boolean;
    type: IJavaType;
    rawType: IClass<any>;
}

export interface ITypeIdResolver {
    mechanism: IId;
    descForKnownTypeIds: string;
}

export interface IMergeInfo {
    getter: IAnnotatedMember;
    fromDefaults: boolean;
}

export interface IBeanProperty extends INamed {
    wrapperName: IPropertyName;
    required: boolean;
    member: IAnnotatedMember;
    type: IJavaType;
    virtual: boolean;
    fullName: IPropertyName;
    metadata: IPropertyMetadata;
}

export interface INamed {
    name: string;
}

export interface HttpClient {

    request<R>(requestConfig: { method: string; url: string; queryParams?: any; data?: any; copyFn?: (data: R) => R; }): RestResponse<R>;
}

export class RestApplicationClient {

    constructor(protected httpClient: HttpClient) {
    }

    /**
     * HTTP GET /registration/request
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.getAllRegistrationRequests
     */
    getAllRegistrationRequests(): RestResponse<IRegistrationRequestEntryTO[]> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`registration/request` });
    }

    /**
     * HTTP POST /registration/request
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.registerParticipant
     */
    registerParticipant(request: ICreateRegistrationRequestTO): RestResponse<void> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`registration/request`, data: request });
    }

    /**
     * HTTP GET /registration/request/{did}
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.getRegistrationRequestByDid
     */
    getRegistrationRequestByDid(did: string): RestResponse<IRegistrationRequestEntryTO> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`registration/request/${did}` });
    }

    /**
     * HTTP DELETE /registration/request/{id}
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.deleteRegistrationRequest
     */
    deleteRegistrationRequest(id: string): RestResponse<void> {
        return this.httpClient.request({ method: "DELETE", url: uriEncoding`registration/request/${id}` });
    }

    /**
     * HTTP POST /registration/request/{id}/accept
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.acceptRegistrationRequest
     */
    acceptRegistrationRequest(id: string): RestResponse<void> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`registration/request/${id}/accept` });
    }

    /**
     * HTTP POST /registration/request/{id}/reject
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.rejectRegistrationRequest
     */
    rejectRegistrationRequest(id: string): RestResponse<void> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`registration/request/${id}/reject` });
    }

    /**
     * HTTP GET /shapes/gx/legalparticipant
     * Java method: eu.possiblex.portal.application.boundary.ParticipantShapeRestApiImpl.getGxLegalParticipantShape
     */
    getGxLegalParticipantShape(): RestResponse<string> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`shapes/gx/legalparticipant` });
    }

    /**
     * HTTP GET /shapes/gx/legalregistrationnumber
     * Java method: eu.possiblex.portal.application.boundary.ParticipantShapeRestApiImpl.getGxLegalRegistrationNumberShape
     */
    getGxLegalRegistrationNumberShape(): RestResponse<string> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`shapes/gx/legalregistrationnumber` });
    }

    /**
     * HTTP GET /shapes/px/participantextension
     * Java method: eu.possiblex.portal.application.boundary.ParticipantShapeRestApiImpl.getPxParticipantExtension
     */
    getPxParticipantExtension(): RestResponse<string> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`shapes/px/participantextension` });
    }
}

export type RestResponse<R> = Promise<R>;

export type IPojoCredentialSubjectUnion = IGxLegalParticipantCredentialSubject | IGxLegalRegistrationNumberCredentialSubject | IPxParticipantExtensionCredentialSubject;

export const enum IRequestStatus {
    NEW = "NEW",
    ACCEPTED = "ACCEPTED",
    REJECTED = "REJECTED",
    COMPLETED = "COMPLETED",
}

export const enum IAccessPattern {
    ALWAYS_NULL = "ALWAYS_NULL",
    CONSTANT = "CONSTANT",
    DYNAMIC = "DYNAMIC",
}

export const enum IAs {
    PROPERTY = "PROPERTY",
    WRAPPER_OBJECT = "WRAPPER_OBJECT",
    WRAPPER_ARRAY = "WRAPPER_ARRAY",
    EXTERNAL_PROPERTY = "EXTERNAL_PROPERTY",
    EXISTING_PROPERTY = "EXISTING_PROPERTY",
}

export const enum INulls {
    SET = "SET",
    SKIP = "SKIP",
    FAIL = "FAIL",
    AS_EMPTY = "AS_EMPTY",
    DEFAULT = "DEFAULT",
}

export const enum IId {
    NONE = "NONE",
    CLASS = "CLASS",
    MINIMAL_CLASS = "MINIMAL_CLASS",
    NAME = "NAME",
    DEDUCTION = "DEDUCTION",
    CUSTOM = "CUSTOM",
}

function uriEncoding(template: TemplateStringsArray, ...substitutions: any[]): string {
    let result = "";
    for (let i = 0; i < substitutions.length; i++) {
        result += template[i];
        result += encodeURIComponent(substitutions[i]);
    }
    result += template[template.length - 1];
    return result;
}
