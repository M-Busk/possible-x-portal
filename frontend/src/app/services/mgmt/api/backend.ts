/* tslint:disable */
/* eslint-disable */

export interface IParticipantRegistrationRestApi {
    allRegistrationRequests: IRegistrationRequestListTO[];
}

export interface IParticipantShapeRestApi {
    gxLegalParticipantShape: string;
    gxLegalRegistrationNumberShape: string;
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

export interface IRegistrationNumberTO {
    eori: string;
    vatID: string;
    leiCode: string;
}

export interface IRegistrationNumberTOBuilder {
}

export interface IRegistrationRequestListTO {
    legalRegistrationNumber: IRegistrationNumberTO;
    legalAddress: IAddressTO;
    headquarterAddress: IAddressTO;
    name: string;
    description: string;
}

export interface IRegistrationRequestListTOBuilder {
}

export interface IRegistrationRequestTO {
    participantCs: IGxLegalParticipantCredentialSubject;
    registrationNumberCs: IGxLegalRegistrationNumberCredentialSubject;
}

export interface IRegistrationRequestTOBuilder {
}

export interface IPojoCredentialSubject {
    "@type": "UnknownCredentialSubject" | "gx:LegalParticipant" | "gx:legalRegistrationNumber";
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

export interface IClass<T> extends ISerializable, IGenericDeclaration, IType, IAnnotatedElement, IOfField<IClass<any>>, IConstable {
}

export interface IValueInstantiator {
    arrayDelegateCreator: IAnnotatedWithParams;
    delegateCreator: IAnnotatedWithParams;
    valueClass: IClass<any>;
    withArgsCreator: IAnnotatedWithParams;
    valueTypeDesc: string;
    defaultCreator: IAnnotatedWithParams;
}

export interface IJavaType extends IResolvedType, ISerializable, IType {
    javaLangObject: boolean;
    typeHandler: any;
    valueHandler: any;
    enumImplType: boolean;
    recordType: boolean;
    contentValueHandler: any;
    contentTypeHandler: any;
    erasedSignature: string;
    superClass: IJavaType;
    keyType: IJavaType;
    referencedType: IJavaType;
    interfaces: IJavaType[];
    genericSignature: string;
    contentType: IJavaType;
    bindings: ITypeBindings;
}

export interface IJsonDeserializer<T> extends INullValueProvider {
    emptyAccessPattern: IAccessPattern;
    delegatee: IJsonDeserializer<any>;
    knownPropertyNames: any[];
    objectIdReader: IObjectIdReader;
    /**
     * @deprecated
     */
    emptyValue: any;
    cachable: boolean;
    /**
     * @deprecated
     */
    nullValue: T;
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
    delegatee: IJsonSerializer<any>;
    unwrappingSerializer: boolean;
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
    concrete: boolean;
    collectionLikeType: boolean;
    mapLikeType: boolean;
    /**
     * @deprecated
     */
    parameterSource: IClass<any>;
    containerType: boolean;
    enumType: boolean;
    throwable: boolean;
    rawClass: IClass<any>;
    keyType: IResolvedType;
    referencedType: IResolvedType;
    arrayType: boolean;
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
    managedReferenceName: string;
    valueTypeDeserializer: ITypeDeserializer;
    nullValueProvider: INullValueProvider;
    propertyIndex: number;
    injectableValueId: any;
    injectionOnly: boolean;
    valueDeserializer: IJsonDeserializer<any>;
    objectIdInfo: IObjectIdInfo;
    creatorIndex: number;
    ignorable: boolean;
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

export interface IMember {
    name: string;
    modifiers: number;
    synthetic: boolean;
    declaringClass: IClass<any>;
}

export interface IAnnotationMap extends IAnnotations {
}

export interface ITypeResolutionContext {
}

export interface IAnnotatedMember extends IAnnotated, ISerializable {
    member: IMember;
    allAnnotations: IAnnotationMap;
    /**
     * @deprecated
     */
    typeContext: ITypeResolutionContext;
    declaringClass: IClass<any>;
    fullName: string;
}

export interface ITypeDeserializer {
    typeInclusion: IAs;
    typeIdResolver: ITypeIdResolver;
    defaultImpl: IClass<any>;
    propertyName: string;
}

export interface IObjectIdInfo {
    alwaysAsId: boolean;
    generatorType: IClass<IObjectIdGenerator<any>>;
    resolverType: IClass<IObjectIdResolver>;
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
    descForKnownTypeIds: string;
    mechanism: IId;
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
    getAllRegistrationRequests(): RestResponse<IRegistrationRequestListTO[]> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`registration/request` });
    }

    /**
     * HTTP POST /registration/request
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.registerParticipant
     */
    registerParticipant(request: IRegistrationRequestTO): RestResponse<void> {
        return this.httpClient.request({ method: "POST", url: uriEncoding`registration/request`, data: request });
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
}

export type RestResponse<R> = Promise<R>;

export type IAccessPattern = "ALWAYS_NULL" | "CONSTANT" | "DYNAMIC";

export type IAs = "PROPERTY" | "WRAPPER_OBJECT" | "WRAPPER_ARRAY" | "EXTERNAL_PROPERTY" | "EXISTING_PROPERTY";

export type INulls = "SET" | "SKIP" | "FAIL" | "AS_EMPTY" | "DEFAULT";

export type IId = "NONE" | "CLASS" | "MINIMAL_CLASS" | "NAME" | "DEDUCTION" | "CUSTOM";

export type IPojoCredentialSubjectUnion = IGxLegalParticipantCredentialSubject | IGxLegalRegistrationNumberCredentialSubject;

function uriEncoding(template: TemplateStringsArray, ...substitutions: any[]): string {
    let result = "";
    for (let i = 0; i < substitutions.length; i++) {
        result += template[i];
        result += encodeURIComponent(substitutions[i]);
    }
    result += template[template.length - 1];
    return result;
}
