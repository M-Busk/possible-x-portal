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

/* tslint:disable */
/* eslint-disable */

export interface ICommonPortalRestApi {
    environment: IEnvironmentTO;
    version: IVersionTO;
}

export interface IParticipantRegistrationRestApi {
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

export interface IEnvironmentTO {
    catalogUiUrl: string;
}

export interface IEnvironmentTOBuilder {
}

export interface IErrorResponseTO {
    timestamp: Date;
    message: string;
    details: string;
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

export interface IVersionTO {
    version: string;
    date: string;
}

export interface IVersionTOBuilder {
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
    "schema:name": string;
    "schema:description": string;
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

export interface IJavaType extends IResolvedType, ISerializable, IType {
    javaLangObject: boolean;
    recordType: boolean;
    typeHandler: any;
    valueHandler: any;
    enumImplType: boolean;
    keyType: IJavaType;
    referencedType: IJavaType;
    contentValueHandler: any;
    contentTypeHandler: any;
    erasedSignature: string;
    superClass: IJavaType;
    interfaces: IJavaType[];
    genericSignature: string;
    bindings: ITypeBindings;
    contentType: IJavaType;
}

export interface IValueInstantiator {
    valueClass: IClass<any>;
    arrayDelegateCreator: IAnnotatedWithParams;
    delegateCreator: IAnnotatedWithParams;
    withArgsCreator: IAnnotatedWithParams;
    valueTypeDesc: string;
    defaultCreator: IAnnotatedWithParams;
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

export interface IPage<T> extends ISlice<T> {
    totalPages: number;
    totalElements: number;
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
    keyType: IResolvedType;
    referencedType: IResolvedType;
    /**
     * @deprecated
     */
    parameterSource: IClass<any>;
    throwable: boolean;
    rawClass: IClass<any>;
    interface: boolean;
    primitive: boolean;
    final: boolean;
    abstract: boolean;
    referenceType: boolean;
    contentType: IResolvedType;
}

export interface IAnnotatedWithParams extends IAnnotatedMember {
    annotationCount: number;
    parameterCount: number;
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
    injectableValueId: any;
    valueDeserializer: IJsonDeserializer<any>;
    creatorIndex: number;
    objectIdInfo: IObjectIdInfo;
    managedReferenceName: string;
    ignorable: boolean;
    valueTypeDeserializer: ITypeDeserializer;
    nullValueProvider: INullValueProvider;
    propertyIndex: number;
    injectionOnly: boolean;
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

export interface IPageable {
    paged: boolean;
    unpaged: boolean;
    pageNumber: number;
    pageSize: number;
    offset: number;
    sort: ISort;
}

export interface ISort extends IStreamable<IOrder>, ISerializable {
    sorted: boolean;
    unsorted: boolean;
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

export interface IAnnotationMap extends IAnnotations {
}

export interface ITypeResolutionContext {
}

export interface IMember {
    name: string;
    modifiers: number;
    synthetic: boolean;
    declaringClass: IClass<any>;
}

export interface IAnnotatedMember extends IAnnotated, ISerializable {
    allAnnotations: IAnnotationMap;
    /**
     * @deprecated
     */
    typeContext: ITypeResolutionContext;
    member: IMember;
    declaringClass: IClass<any>;
    fullName: string;
}

export interface IObjectIdInfo {
    generatorType: IClass<IObjectIdGenerator<any>>;
    resolverType: IClass<IObjectIdResolver>;
    alwaysAsId: boolean;
    propertyName: IPropertyName;
    scope: IClass<any>;
}

export interface ITypeDeserializer {
    typeIdResolver: ITypeIdResolver;
    defaultImpl: IClass<any>;
    typeInclusion: IAs;
    propertyName: string;
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

export interface ISlice<T> extends IStreamable<T> {
    numberOfElements: number;
    pageable: IPageable;
    first: boolean;
    last: boolean;
    size: number;
    content: T[];
    number: number;
    sort: ISort;
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

export interface IStreamable<T> extends IIterable<T>, ISupplier<IStream<T>> {
    empty: boolean;
}

export interface IOrder extends ISerializable {
    direction: IDirection;
    property: string;
    ignoreCase: boolean;
    nullHandling: INullHandling;
    ascending: boolean;
    descending: boolean;
}

export interface INamed {
    name: string;
}

export interface IIterable<T> {
}

export interface ISupplier<T> {
}

export interface IStream<T> extends IBaseStream<T, IStream<T>> {
}

export interface IBaseStream<T, S> extends IAutoCloseable {
    parallel: boolean;
}

export interface IAutoCloseable {
}

export interface HttpClient {

    request<R>(requestConfig: { method: string; url: string; queryParams?: any; data?: any; copyFn?: (data: R) => R; }): RestResponse<R>;
}

export class RestApplicationClient {

    constructor(protected httpClient: HttpClient) {
    }

    /**
     * HTTP GET /common/environment
     * Java method: eu.possiblex.portal.application.boundary.CommonPortalRestApiImpl.getEnvironment
     */
    getEnvironment(): RestResponse<IEnvironmentTO> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`common/environment` });
    }

    /**
     * HTTP GET /common/version
     * Java method: eu.possiblex.portal.application.boundary.CommonPortalRestApiImpl.getVersion
     */
    getVersion(): RestResponse<IVersionTO> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`common/version` });
    }

    /**
     * HTTP GET /registration/request
     * Java method: eu.possiblex.portal.application.boundary.ParticipantRegistrationRestApiImpl.getRegistrationRequests
     */
    getRegistrationRequests(queryParams?: { page?: number; size?: number; sort?: string; }): RestResponse<IPage<IRegistrationRequestEntryTO>> {
        return this.httpClient.request({ method: "GET", url: uriEncoding`registration/request`, queryParams: queryParams });
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

export const enum IDirection {
    ASC = "ASC",
    DESC = "DESC",
}

export const enum INullHandling {
    NATIVE = "NATIVE",
    NULLS_FIRST = "NULLS_FIRST",
    NULLS_LAST = "NULLS_LAST",
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
