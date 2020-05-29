package com.bbva.datio.datahubpe.modelMatrixIC.config


/** Class Parameter for process data of model Matrix IC.
 *
 *  @constructor create a new Parameter with parameters: processDate and processToday and processType and tempDir and local and fields and entitys
 *  @param processDate
 *  @param processToday
 *  @param processType
 *  @param tempDir
 *  @param local
 *  @param fields
 *  @param entitys
 */
case class Parameter(processDate: String,
                     processToday: String,
                     processType: String,
                     tempDir: String,
                     local: Local,
                     fields: Fields,
                     entitys: Entitys
                    )

/** Class Local for process data of model Matrix IC.
 *
 *  @constructor create a new Local with parameters: bbvaEntityID and residenceType and geolocSeparator and addressVoid and sourceSeparator and employeeType and operareaId2664 and operareaId2665 and retailBankOutTypes and employeesOutTypes and productBBVAGroupProcessCode and participantType and currentExpiryDate and cardStatusTypeList and cardBlockTypeList and contractCancelDate and productGroupTypes and productGroupValues and creatingServiceIdList and customerValueGroupProcessCode and matrixCampaignDefaultValue and matrixDefaultStructure
 *  @param bbvaEntityID
 *  @param residenceType
 *  @param geolocSeparator
 *  @param addressVoid
 *  @param sourceSeparator
 *  @param employeeType
 *  @param operareaId2664
 *  @param operareaId2665
 *  @param retailBankOutTypes
 *  @param employeesOutTypes
 *  @param productBBVAGroupProcessCode
 *  @param participantType
 *  @param currentExpiryDate
 *  @param cardStatusTypeList
 *  @param cardBlockTypeList
 *  @param contractCancelDate
 *  @param productGroupTypes
 *  @param productGroupValues
 *  @param creatingServiceIdList
 *  @param customerValueGroupProcessCode
 *  @param matrixCampaignDefaultValue
 *  @param matrixDefaultStructure
 */
case class Local(bbvaEntityID: String,
                 residenceType: String,
                 geolocSeparator: String,
                 addressVoid: String,
                 sourceSeparator: String,
                 employeeType: String,
                 operareaId2664: String,
                 operareaId2665: String,
                 retailBankOutTypes: java.util.List[String],
                 employeesOutTypes: java.util.List[String],
                 productBBVAGroupProcessCode: String,
                 participantType: String,
                 currentExpiryDate: String,
                 cardStatusTypeList: java.util.List[String],
                 cardBlockTypeList: java.util.List[String],
                 contractCancelDate: String,
                 productGroupTypes: java.util.List[String],
                 productGroupValues: java.util.List[String],
                 creatingServiceIdList: java.util.List[String],
                 customerValueGroupProcessCode: String,
                 matrixCampaignDefaultValue: String,
                 matrixDefaultStructure: java.util.List[String]
                )

/** Class Fields for process data of model Matrix IC.
 *
 *  @constructor create a new Fields with parameters: peopleBoardAggregate and sunatPeople and branchCatalog and hierarchyBranch and employee and payrollBdphMov and manager and segmentGroup and geoLocationMaster and taxPayerDuns and address and addressContactability and reniec and matrixIcOut and String] and parameterFileIc and peopleDaily and balanceAggregateMonthly and productSubproduct and cardMaster and String] and productLoanDaily and bin and balanceMonthlyCorp and contractMaster and contactability and accountSbsAggregate and codeSbsAggregate and rccBalance
 *  @param peopleBoardAggregate
 *  @param sunatPeople
 *  @param branchCatalog
 *  @param hierarchyBranch
 *  @param employee
 *  @param payrollBdphMov
 *  @param manager
 *  @param segmentGroup
 *  @param geoLocationMaster
 *  @param taxPayerDuns
 *  @param address
 *  @param addressContactability
 *  @param reniec
 *  @param matrixIcOut
 *  @param String]
 *  @param parameterFileIc
 *  @param peopleDaily
 *  @param balanceAggregateMonthly
 *  @param productSubproduct
 *  @param cardMaster
 *  @param String]
 *  @param productLoanDaily
 *  @param bin
 *  @param balanceMonthlyCorp
 *  @param contractMaster
 *  @param contactability
 *  @param accountSbsAggregate
 *  @param codeSbsAggregate
 *  @param rccBalance
 */
case class Fields(peopleBoardAggregate: PeopleBoardAggregate,
                  sunatPeople: SunatPeople,
                  branchCatalog: BranchCatalog,
                  hierarchyBranch: HierarchyBranch,
                  employee: Employee,
                  payrollBdphMov: PayrollBdphMov,
                  manager: Manager,
                  segmentGroup: SegmentGroup,
                  geoLocationMaster: GeoLocationMaster,
                  taxPayerDuns: TaxPayerDuns,
                  address: Address,
                  addressContactability: AddressContactability,
                  reniec: Reniec,
                  matrixIcOut: java.util.HashMap[String, String],
                  parameterFileIc: ParameterFileIc,
                  peopleDaily: PeopleDaily,
                  balanceAggregateMonthly: BalanceAggregateMonthly,
                  productSubproduct: ProductSubproduct,
                  cardMaster: java.util.HashMap[String, String],
                  productLoanDaily: ProductLoanDaily,
                  bin: Bin,
                  balanceMonthlyCorp: BalanceMonthlyCorp,
                  contractMaster: ContractMaster,
                  contactability: Contactability,
                  accountSbsAggregate:AccountSbsAggregate,
                  codeSbsAggregate:CodeSbsAggregate,
                  rccBalance:RccBalance
                 )

/** Class PeopleBoardAggregate for process data of model Matrix IC.
 *
 *  @constructor create a new PeopleBoardAggregate with parameters: personalId and personalType and customerId and firstName and lastName and secondLastName and documentType and genderType and birthDate and maritalStatusType and defaultsfOpType and segmentId and customerStatusType and persDataProtectType and customerCashPoolType and defaultSituationComType and sbsCustomerId and admissionDate and commercialProductId and ccrDefaultOpType and lastDefaultCcrAmount and lastDefaultCcrDate and unwantedType and onlineBankingAffiliationType and smsAffiliationType and ageNumber and worstBalanceQualificationType and bureauScoreGroupType and spousePersonalType and spousePersonalId and robinsonType and incomeMonthlyAmount and incomeMonthlySourceDesc and customerPortfolioType and custBranchId and monthlyDebtAmount and receivablePaymentType and customerActivityType and taxPaymentType and companyEconomicActivityId and addressGeolocationId and taxpayerId and mainManagerId and riskFailedCampaignType and commercialFailedCampaignType and employerTaxpayerDunsId and portfolioAllocationType and statusTaxpayerType and audtiminsertDate
 *  @param personalId
 *  @param personalType
 *  @param customerId
 *  @param firstName
 *  @param lastName
 *  @param secondLastName
 *  @param documentType
 *  @param genderType
 *  @param birthDate
 *  @param maritalStatusType
 *  @param defaultsfOpType
 *  @param segmentId
 *  @param customerStatusType
 *  @param persDataProtectType
 *  @param customerCashPoolType
 *  @param defaultSituationComType
 *  @param sbsCustomerId
 *  @param admissionDate
 *  @param commercialProductId
 *  @param ccrDefaultOpType
 *  @param lastDefaultCcrAmount
 *  @param lastDefaultCcrDate
 *  @param unwantedType
 *  @param onlineBankingAffiliationType
 *  @param smsAffiliationType
 *  @param ageNumber
 *  @param worstBalanceQualificationType
 *  @param bureauScoreGroupType
 *  @param spousePersonalType
 *  @param spousePersonalId
 *  @param robinsonType
 *  @param incomeMonthlyAmount
 *  @param incomeMonthlySourceDesc
 *  @param customerPortfolioType
 *  @param custBranchId
 *  @param monthlyDebtAmount
 *  @param receivablePaymentType
 *  @param customerActivityType
 *  @param taxPaymentType
 *  @param companyEconomicActivityId
 *  @param addressGeolocationId
 *  @param taxpayerId
 *  @param mainManagerId
 *  @param riskFailedCampaignType
 *  @param commercialFailedCampaignType
 *  @param employerTaxpayerDunsId
 *  @param portfolioAllocationType
 *  @param statusTaxpayerType
 *  @param audtiminsertDate
 */
case class PeopleBoardAggregate(personalId: String,
                                personalType: String,
                                customerId: String,
                                firstName: String,
                                lastName: String,
                                secondLastName: String,
                                documentType: String,
                                genderType: String,
                                birthDate: String,
                                maritalStatusType: String,
                                defaultsfOpType: String,
                                segmentId: String,
                                customerStatusType: String,
                                persDataProtectType: String,
                                customerCashPoolType: String,
                                defaultSituationComType: String,
                                sbsCustomerId: String,
                                admissionDate: String,
                                commercialProductId: String,
                                ccrDefaultOpType: String,
                                lastDefaultCcrAmount: String,
                                lastDefaultCcrDate: String,
                                unwantedType: String,
                                onlineBankingAffiliationType: String,
                                smsAffiliationType: String,
                                ageNumber: String,
                                worstBalanceQualificationType: String,
                                bureauScoreGroupType: String,
                                spousePersonalType: String,
                                spousePersonalId: String,
                                robinsonType: String,
                                incomeMonthlyAmount: String,
                                incomeMonthlySourceDesc: String,
                                customerPortfolioType: String,
                                custBranchId: String,
                                monthlyDebtAmount: String,
                                receivablePaymentType: String,
                                customerActivityType: String,
                                taxPaymentType: String,
                                companyEconomicActivityId: String,
                                addressGeolocationId: String,
                                taxpayerId: String,
                                mainManagerId: String,
                                riskFailedCampaignType: String,
                                commercialFailedCampaignType: String,
                                employerTaxpayerDunsId: String,
                                portfolioAllocationType: String,
                                statusTaxpayerType: String,
                                audtiminsertDate: String
                               )

/** Class SunatPeople for process data of model Matrix IC.
 *
 *  @constructor create a new SunatPeople with parameters: taxpayerId and audtiminsertDate and roadTypeId and customerName and addressDesc and roadNumberId and licenseMunicipalNumber and registryEntryDate and workFaxId and externalTradeType and personalId and additionalAddressDataDesc and phoneNumber2Id and correlativeNumber and companyTradeName and phoneNumberId and statusTaxpayerType and urbanizationName and enrollmentType and returnResultType and taxpayerStartDate and dependenceOriginId and urbanizationType and oldTaxpayerId and processNumber and incorporationDate and commStartDate and dependenceSunatId and companyEconomicActivityId and transactionType and addressRoadNumberId and taxpayerEndDate and birthDate and cutoffDate and personalType and phoneNumber1Id and taxpayerType and zipcodeId
 *  @param taxpayerId
 *  @param audtiminsertDate
 *  @param roadTypeId
 *  @param customerName
 *  @param addressDesc
 *  @param roadNumberId
 *  @param licenseMunicipalNumber
 *  @param registryEntryDate
 *  @param workFaxId
 *  @param externalTradeType
 *  @param personalId
 *  @param additionalAddressDataDesc
 *  @param phoneNumber2Id
 *  @param correlativeNumber
 *  @param companyTradeName
 *  @param phoneNumberId
 *  @param statusTaxpayerType
 *  @param urbanizationName
 *  @param enrollmentType
 *  @param returnResultType
 *  @param taxpayerStartDate
 *  @param dependenceOriginId
 *  @param urbanizationType
 *  @param oldTaxpayerId
 *  @param processNumber
 *  @param incorporationDate
 *  @param commStartDate
 *  @param dependenceSunatId
 *  @param companyEconomicActivityId
 *  @param transactionType
 *  @param addressRoadNumberId
 *  @param taxpayerEndDate
 *  @param birthDate
 *  @param cutoffDate
 *  @param personalType
 *  @param phoneNumber1Id
 *  @param taxpayerType
 *  @param zipcodeId
 */
case class SunatPeople(taxpayerId: String,
                      audtiminsertDate: String,
                      roadTypeId: String,
                      customerName: String,
                      addressDesc: String,
                      roadNumberId: String,
                      licenseMunicipalNumber: String,
                      registryEntryDate: String,
                      workFaxId: String,
                      externalTradeType: String,
                      personalId: String,
                      additionalAddressDataDesc: String,
                      phoneNumber2Id: String,
                      correlativeNumber: String,
                      companyTradeName: String,
                      phoneNumberId: String,
                      statusTaxpayerType: String,
                      urbanizationName: String,
                      enrollmentType: String,
                      returnResultType: String,
                      taxpayerStartDate: String,
                      dependenceOriginId: String,
                      urbanizationType: String,
                      oldTaxpayerId: String,
                      processNumber: String,
                      incorporationDate: String,
                      commStartDate: String,
                      dependenceSunatId: String,
                      companyEconomicActivityId: String,
                      transactionType: String,
                      addressRoadNumberId: String,
                      taxpayerEndDate: String,
                      birthDate: String,
                      cutoffDate: String,
                      personalType: String,
                      phoneNumber1Id: String,
                      taxpayerType: String,
                      zipcodeId: String
                      )

/** Class BranchCatalog for process data of model Matrix IC.
 *
 *  @constructor create a new BranchCatalog with parameters: accountInstallDate and addressGeolocationId and branchPhoneNumber1Id and customLangId and firstEntryDate and reflexEntityId and finalReplBranchId and listDistBranchId and listRemote2DistId and operationalBranchType and printIndicatorType and registerAddressName and sedexBranchId and backOfficeBranchId and branchStatusType and branchDesc and exchangeDate and representBranchId and campaignContacType and branchConnCttType and centralAdminId and entityId and locGeographicType and sedexEntityId and finalReplEntityId and branchCategoryId and listDistEntityId and replEntityId and branchPhoneNumberId and branchZipId and internalCountryId and massiveTypeId and releasaBranchType and accountCloseIndType and branchFaxId and cutoffDate and internalEntityId and vatBranchId and branchCloseDate and telexId and optaChangeDate and releaseDate and contractEndDate and openAfternoonsType and md5CountryExchangeId and onlineAddressDesc and accountingCloseId and efanAreaId and locationBranchType and prejudicialBranchId and backOfficeDexBranchId and foreignBranchId and listPrior1DistNumber and audtiminsertDate and childrenBranchLevelId and failManagUnitId and accountUnitId and analyticalAccountType and fictBranchType and integraBranchEntityId and mutualBranchId and regionalAdminId and seniorHierBranchId and closeIndBranchType and taxAgencyId and branchBusinessTypeId and branchOpenDate and callCenterPhoneNumberId and complexLevelId and defaultBranchId and dirMarketingCenterId and geoGroupAutonomyId and listPrior2DistNumber and nonJudicialBranchId and listRemote1DistId and accountSystemId and branchNotesIndType and branchSuperiorId and centralAccountType and departCostCenterId and employLangId and glAccountBranchId and mortgageCenterId and ocaBranchId and opplusBranchType and areaFreeBusinessId and geoGroupLocId and md5BranchType and mutualEntityId and poBoxId and reopeningBranchType and teleprocessTypeId and branchId and integratedBranchId and geoTypeAutonomyId and issuingProvinceName and replBranchId and locCountryId and singeOperAreaId and unitType
 *  @param accountInstallDate
 *  @param addressGeolocationId
 *  @param branchPhoneNumber1Id
 *  @param customLangId
 *  @param firstEntryDate
 *  @param reflexEntityId
 *  @param finalReplBranchId
 *  @param listDistBranchId
 *  @param listRemote2DistId
 *  @param operationalBranchType
 *  @param printIndicatorType
 *  @param registerAddressName
 *  @param sedexBranchId
 *  @param backOfficeBranchId
 *  @param branchStatusType
 *  @param branchDesc
 *  @param exchangeDate
 *  @param representBranchId
 *  @param campaignContacType
 *  @param branchConnCttType
 *  @param centralAdminId
 *  @param entityId
 *  @param locGeographicType
 *  @param sedexEntityId
 *  @param finalReplEntityId
 *  @param branchCategoryId
 *  @param listDistEntityId
 *  @param replEntityId
 *  @param branchPhoneNumberId
 *  @param branchZipId
 *  @param internalCountryId
 *  @param massiveTypeId
 *  @param releasaBranchType
 *  @param accountCloseIndType
 *  @param branchFaxId
 *  @param cutoffDate
 *  @param internalEntityId
 *  @param vatBranchId
 *  @param branchCloseDate
 *  @param telexId
 *  @param optaChangeDate
 *  @param releaseDate
 *  @param contractEndDate
 *  @param openAfternoonsType
 *  @param md5CountryExchangeId
 *  @param onlineAddressDesc
 *  @param accountingCloseId
 *  @param efanAreaId
 *  @param locationBranchType
 *  @param prejudicialBranchId
 *  @param backOfficeDexBranchId
 *  @param foreignBranchId
 *  @param listPrior1DistNumber
 *  @param audtiminsertDate
 *  @param childrenBranchLevelId
 *  @param failManagUnitId
 *  @param accountUnitId
 *  @param analyticalAccountType
 *  @param fictBranchType
 *  @param integraBranchEntityId
 *  @param mutualBranchId
 *  @param regionalAdminId
 *  @param seniorHierBranchId
 *  @param closeIndBranchType
 *  @param taxAgencyId
 *  @param branchBusinessTypeId
 *  @param branchOpenDate
 *  @param callCenterPhoneNumberId
 *  @param complexLevelId
 *  @param defaultBranchId
 *  @param dirMarketingCenterId
 *  @param geoGroupAutonomyId
 *  @param listPrior2DistNumber
 *  @param nonJudicialBranchId
 *  @param listRemote1DistId
 *  @param accountSystemId
 *  @param branchNotesIndType
 *  @param branchSuperiorId
 *  @param centralAccountType
 *  @param departCostCenterId
 *  @param employLangId
 *  @param glAccountBranchId
 *  @param mortgageCenterId
 *  @param ocaBranchId
 *  @param opplusBranchType
 *  @param areaFreeBusinessId
 *  @param geoGroupLocId
 *  @param md5BranchType
 *  @param mutualEntityId
 *  @param poBoxId
 *  @param reopeningBranchType
 *  @param teleprocessTypeId
 *  @param branchId
 *  @param integratedBranchId
 *  @param geoTypeAutonomyId
 *  @param issuingProvinceName
 *  @param replBranchId
 *  @param locCountryId
 *  @param singeOperAreaId
 *  @param unitType
 */
case class BranchCatalog(accountInstallDate: String,
                        addressGeolocationId: String,
                        branchPhoneNumber1Id: String,
                        customLangId: String,
                        firstEntryDate: String,
                        reflexEntityId: String,
                        finalReplBranchId: String,
                        listDistBranchId: String,
                        listRemote2DistId: String,
                        operationalBranchType: String,
                        printIndicatorType: String,
                        registerAddressName: String,
                        sedexBranchId: String,
                        backOfficeBranchId: String,
                        branchStatusType: String,
                        branchDesc: String,
                        exchangeDate: String,
                        representBranchId: String,
                        campaignContacType: String,
                        branchConnCttType: String,
                        centralAdminId: String,
                        entityId: String,
                        locGeographicType: String,
                        sedexEntityId: String,
                        finalReplEntityId: String,
                        branchCategoryId: String,
                        listDistEntityId: String,
                        replEntityId: String,
                        branchPhoneNumberId: String,
                        branchZipId: String,
                        internalCountryId: String,
                        massiveTypeId: String,
                        releasaBranchType: String,
                        accountCloseIndType: String,
                        branchFaxId: String,
                        cutoffDate: String,
                        internalEntityId: String,
                        vatBranchId: String,
                        branchCloseDate: String,
                        telexId: String,
                        optaChangeDate: String,
                        releaseDate: String,
                        contractEndDate: String,
                        openAfternoonsType: String,
                        md5CountryExchangeId: String,
                        onlineAddressDesc: String,
                        accountingCloseId: String,
                        efanAreaId: String,
                        locationBranchType: String,
                        prejudicialBranchId: String,
                        backOfficeDexBranchId: String,
                        foreignBranchId: String,
                        listPrior1DistNumber: String,
                        audtiminsertDate: String,
                        childrenBranchLevelId: String,
                        failManagUnitId: String,
                        accountUnitId: String,
                        analyticalAccountType: String,
                        fictBranchType: String,
                        integraBranchEntityId: String,
                        mutualBranchId: String,
                        regionalAdminId: String,
                        seniorHierBranchId: String,
                        closeIndBranchType: String,
                        taxAgencyId: String,
                        branchBusinessTypeId: String,
                        branchOpenDate: String,
                        callCenterPhoneNumberId: String,
                        complexLevelId: String,
                        defaultBranchId: String,
                        dirMarketingCenterId: String,
                        geoGroupAutonomyId: String,
                        listPrior2DistNumber: String,
                        nonJudicialBranchId: String,
                        listRemote1DistId: String,
                        accountSystemId: String,
                        branchNotesIndType: String,
                        branchSuperiorId: String,
                        centralAccountType: String,
                        departCostCenterId: String,
                        employLangId: String,
                        glAccountBranchId: String,
                        mortgageCenterId: String,
                        ocaBranchId: String,
                        opplusBranchType: String,
                        areaFreeBusinessId: String,
                        geoGroupLocId: String,
                        md5BranchType: String,
                        mutualEntityId: String,
                        poBoxId: String,
                        reopeningBranchType: String,
                        teleprocessTypeId: String,
                        branchId: String,
                        integratedBranchId: String,
                        geoTypeAutonomyId: String,
                        issuingProvinceName: String,
                        replBranchId: String,
                        locCountryId: String,
                        singeOperAreaId: String,
                        unitType: String
                        )

/** Class HierarchyBranch for process data of model Matrix IC.
 *
 *  @constructor create a new HierarchyBranch with parameters: dataDate and level55GenManagId and branchId and cutoffDate and level70EntityId and audtiminsertDate and level60OperareaId and childrenBranchLevelId and level20AdjuntiadagId and level40RegionalId and level68DivisionId and level50TerritorialId and level30ZoneId and level65SupraareaId and entityId and level58SupraterritorialId
 *  @param dataDate
 *  @param level55GenManagId
 *  @param branchId
 *  @param cutoffDate
 *  @param level70EntityId
 *  @param audtiminsertDate
 *  @param level60OperareaId
 *  @param childrenBranchLevelId
 *  @param level20AdjuntiadagId
 *  @param level40RegionalId
 *  @param level68DivisionId
 *  @param level50TerritorialId
 *  @param level30ZoneId
 *  @param level65SupraareaId
 *  @param entityId
 *  @param level58SupraterritorialId
 */
case class HierarchyBranch(dataDate: String,
                           level55GenManagId: String,
                           branchId: String,
                           cutoffDate: String,
                           level70EntityId: String,
                           audtiminsertDate: String,
                           level60OperareaId: String,
                           childrenBranchLevelId: String,
                           level20AdjuntiadagId: String,
                           level40RegionalId: String,
                           level68DivisionId: String,
                           level50TerritorialId: String,
                           level30ZoneId: String,
                           level65SupraareaId: String,
                           entityId: String,
                           level58SupraterritorialId: String
                          )

/** Class Employee for process data of model Matrix IC.
 *
 *  @constructor create a new Employee with parameters: cellPhoneNumberId and hiringDate and l4CompanySuperiorUnitId and audtiminsertDate and birthDate and secondBossUserId and bossUserId and costCenterId and branchDesc and holidaysEndDate and companyHeadType and l10CompanySuperiorUnitId and l9CompanySuperiorUnitId and organizationalUnitId and positionId and userEmailDesc and customerId and cutoffDate and employeePositionType and externalCompanyDesc and userName and externalManagerId and workCenterId and genderType and l10CompanySuperiorUnitDesc and l1CompanySuperiorUnitDesc and l2CompanySuperiorUnitId and l3CompanySuperiorUnitDesc and l4CompanySuperiorUnitDesc and l5CompanySuperiorUnitDesc and l5CompanySuperiorUnitId and l6CompanySuperiorUnitDesc and l6CompanySuperiorUnitId and l7CompanySuperiorUnitDesc and l7CompanySuperiorUnitId and lastName and l8CompanySuperiorUnitId and l9CompanySuperiorUnitDesc and landlineNumberId and localPositionDesc and localPositionId and managerEmployeeId and positionDesc and organizationalUnitName and personalId and secondLastName and personalType and user1Name and user3Name and workCenterDesc and workLandlineNumberId and workPhoneNumberId and l1CompanySuperiorUnitId and positionCompanyId and l2CompanySuperiorUnitDesc and holidaysStartDate and branchId and user2Name and employeeType and externalCompanyId and costCenterDesc and l3CompanySuperiorUnitId and l8CompanySuperiorUnitDesc
 *  @param cellPhoneNumberId
 *  @param hiringDate
 *  @param l4CompanySuperiorUnitId
 *  @param audtiminsertDate
 *  @param birthDate
 *  @param secondBossUserId
 *  @param bossUserId
 *  @param costCenterId
 *  @param branchDesc
 *  @param holidaysEndDate
 *  @param companyHeadType
 *  @param l10CompanySuperiorUnitId
 *  @param l9CompanySuperiorUnitId
 *  @param organizationalUnitId
 *  @param positionId
 *  @param userEmailDesc
 *  @param customerId
 *  @param cutoffDate
 *  @param employeePositionType
 *  @param externalCompanyDesc
 *  @param userName
 *  @param externalManagerId
 *  @param workCenterId
 *  @param genderType
 *  @param l10CompanySuperiorUnitDesc
 *  @param l1CompanySuperiorUnitDesc
 *  @param l2CompanySuperiorUnitId
 *  @param l3CompanySuperiorUnitDesc
 *  @param l4CompanySuperiorUnitDesc
 *  @param l5CompanySuperiorUnitDesc
 *  @param l5CompanySuperiorUnitId
 *  @param l6CompanySuperiorUnitDesc
 *  @param l6CompanySuperiorUnitId
 *  @param l7CompanySuperiorUnitDesc
 *  @param l7CompanySuperiorUnitId
 *  @param lastName
 *  @param l8CompanySuperiorUnitId
 *  @param l9CompanySuperiorUnitDesc
 *  @param landlineNumberId
 *  @param localPositionDesc
 *  @param localPositionId
 *  @param managerEmployeeId
 *  @param positionDesc
 *  @param organizationalUnitName
 *  @param personalId
 *  @param secondLastName
 *  @param personalType
 *  @param user1Name
 *  @param user3Name
 *  @param workCenterDesc
 *  @param workLandlineNumberId
 *  @param workPhoneNumberId
 *  @param l1CompanySuperiorUnitId
 *  @param positionCompanyId
 *  @param l2CompanySuperiorUnitDesc
 *  @param holidaysStartDate
 *  @param branchId
 *  @param user2Name
 *  @param employeeType
 *  @param externalCompanyId
 *  @param costCenterDesc
 *  @param l3CompanySuperiorUnitId
 *  @param l8CompanySuperiorUnitDesc
 */
case class Employee(cellPhoneNumberId: String,
                     hiringDate: String,
                     l4CompanySuperiorUnitId: String,
                     audtiminsertDate: String,
                     birthDate: String,
                     secondBossUserId: String,
                     bossUserId: String,
                     costCenterId: String,
                     branchDesc: String,
                     holidaysEndDate: String,
                     companyHeadType: String,
                     l10CompanySuperiorUnitId: String,
                     l9CompanySuperiorUnitId: String,
                     organizationalUnitId: String,
                     positionId: String,
                     userEmailDesc: String,
                     customerId: String,
                     cutoffDate: String,
                     employeePositionType: String,
                     externalCompanyDesc: String,
                     userName: String,
                     externalManagerId: String,
                     workCenterId: String,
                     genderType: String,
                     l10CompanySuperiorUnitDesc: String,
                     l1CompanySuperiorUnitDesc: String,
                     l2CompanySuperiorUnitId: String,
                     l3CompanySuperiorUnitDesc: String,
                     l4CompanySuperiorUnitDesc: String,
                     l5CompanySuperiorUnitDesc: String,
                     l5CompanySuperiorUnitId: String,
                     l6CompanySuperiorUnitDesc: String,
                     l6CompanySuperiorUnitId: String,
                     l7CompanySuperiorUnitDesc: String,
                     l7CompanySuperiorUnitId: String,
                     lastName: String,
                     l8CompanySuperiorUnitId: String,
                     l9CompanySuperiorUnitDesc: String,
                     landlineNumberId: String,
                     localPositionDesc: String,
                     localPositionId: String,
                     managerEmployeeId: String,
                     positionDesc: String,
                     organizationalUnitName: String,
                     personalId: String,
                     secondLastName: String,
                     personalType: String,
                     user1Name: String,
                     user3Name: String,
                     workCenterDesc: String,
                     workLandlineNumberId: String,
                     workPhoneNumberId: String,
                     l1CompanySuperiorUnitId: String,
                     positionCompanyId: String,
                     l2CompanySuperiorUnitDesc: String,
                     holidaysStartDate: String,
                     branchId: String,
                     user2Name: String,
                     employeeType: String,
                     externalCompanyId: String,
                     costCenterDesc: String,
                     l3CompanySuperiorUnitId: String,
                     l8CompanySuperiorUnitDesc: String
                    )

/** Class PayrollBdphMov for process data of model Matrix IC.
 *
 *  @constructor create a new PayrollBdphMov with parameters: currentContractId and employerContractId and localPayrollMonth7Amount and avgLocalPayrollAmount and cutoffDate and localPayrollMonth11Amount and localPayrollMonth5Amount and transferId and customerId and localPayrollMonth2Amount and employerCustomerId and localPayrollMonth6Amount and localPayrollMonth10Amount and localPayrollMonth12Amount and activePayrollType and audtiminsertDate and localPayrollMonth3Amount and localPayrollMonth1Amount and localPayrollMonth4Amount and localPayrollMonth8Amount and localPayrollMonth9Amount
 *  @param currentContractId
 *  @param employerContractId
 *  @param localPayrollMonth7Amount
 *  @param avgLocalPayrollAmount
 *  @param cutoffDate
 *  @param localPayrollMonth11Amount
 *  @param localPayrollMonth5Amount
 *  @param transferId
 *  @param customerId
 *  @param localPayrollMonth2Amount
 *  @param employerCustomerId
 *  @param localPayrollMonth6Amount
 *  @param localPayrollMonth10Amount
 *  @param localPayrollMonth12Amount
 *  @param activePayrollType
 *  @param audtiminsertDate
 *  @param localPayrollMonth3Amount
 *  @param localPayrollMonth1Amount
 *  @param localPayrollMonth4Amount
 *  @param localPayrollMonth8Amount
 *  @param localPayrollMonth9Amount
 */
case class PayrollBdphMov(currentContractId: String,
                          employerContractId: String,
                          localPayrollMonth7Amount: String,
                          avgLocalPayrollAmount: String,
                          cutoffDate: String,
                          localPayrollMonth11Amount: String,
                          localPayrollMonth5Amount: String,
                          transferId: String,
                          customerId: String,
                          localPayrollMonth2Amount: String,
                          employerCustomerId: String,
                          localPayrollMonth6Amount: String,
                          localPayrollMonth10Amount: String,
                          localPayrollMonth12Amount: String,
                          activePayrollType: String,
                          audtiminsertDate: String,
                          localPayrollMonth3Amount: String,
                          localPayrollMonth1Amount: String,
                          localPayrollMonth4Amount: String,
                          localPayrollMonth8Amount: String,
                          localPayrollMonth9Amount: String
                         )

/** Class Manager for process data of model Matrix IC.
 *
 *  @constructor create a new Manager with parameters: lastName and userName and countryId and externalManagerId and managerEmployeeId and audtiminsertDate and secondLastName and entityId and executivePosId and managerBranchId and cutoffDate
 *  @param lastName
 *  @param userName
 *  @param countryId
 *  @param externalManagerId
 *  @param managerEmployeeId
 *  @param audtiminsertDate
 *  @param secondLastName
 *  @param entityId
 *  @param executivePosId
 *  @param managerBranchId
 *  @param cutoffDate
 */
case class Manager(lastName: String,
                   userName: String,
                   countryId: String,
                   externalManagerId: String,
                   managerEmployeeId: String,
                   audtiminsertDate: String,
                   secondLastName: String,
                   entityId: String,
                   executivePosId: String,
                   managerBranchId: String,
                   cutoffDate: String
                  )

/** Class SegmentGroup for process data of model Matrix IC.
 *
 *  @constructor create a new SegmentGroup with parameters: statusSegmentId and cutoffDate and hierCustSegmentGroupName and segmentName and segmentId and modificationUserId and corpCustSegmentGroupName and custSegmentEndDate and hierCustSegmentName and versionId and custSegmentStartDate and lastDataChangeDate and audtiminsertDate and groupSegmentName and employeeType
 *  @param statusSegmentId
 *  @param cutoffDate
 *  @param hierCustSegmentGroupName
 *  @param segmentName
 *  @param segmentId
 *  @param modificationUserId
 *  @param corpCustSegmentGroupName
 *  @param custSegmentEndDate
 *  @param hierCustSegmentName
 *  @param versionId
 *  @param custSegmentStartDate
 *  @param lastDataChangeDate
 *  @param audtiminsertDate
 *  @param groupSegmentName
 *  @param employeeType
 */
case class SegmentGroup(statusSegmentId: String,
                        cutoffDate: String,
                        hierCustSegmentGroupName: String,
                        segmentName: String,
                        segmentId: String,
                        modificationUserId: String,
                        corpCustSegmentGroupName: String,
                        custSegmentEndDate: String,
                        hierCustSegmentName: String,
                        versionId: String,
                        custSegmentStartDate: String,
                        lastDataChangeDate: String,
                        audtiminsertDate: String,
                        groupSegmentName: String,
                        employeeType: String
                       )

/** Class GeoLocationMaster for process data of model Matrix IC.
 *
 *  @constructor create a new GeoLocationMaster with parameters: audtiminsertDate and countryId and addressDistrictName and provinceName and addressGeolocationId and ineiAddressGeolocationId and entityId and cutoffDate and sunatAddressGeolocationId and addressDepartmentName and reniecAddressGeolocationId
 *  @param audtiminsertDate
 *  @param countryId
 *  @param addressDistrictName
 *  @param provinceName
 *  @param addressGeolocationId
 *  @param ineiAddressGeolocationId
 *  @param entityId
 *  @param cutoffDate
 *  @param sunatAddressGeolocationId
 *  @param addressDepartmentName
 *  @param reniecAddressGeolocationId
 */
case class GeoLocationMaster(audtiminsertDate: String,
                             countryId: String,
                             addressDistrictName: String,
                             provinceName: String,
                             addressGeolocationId: String,
                             ineiAddressGeolocationId: String,
                             entityId: String,
                             cutoffDate: String,
                             sunatAddressGeolocationId: String,
                             addressDepartmentName: String,
                             reniecAddressGeolocationId: String
                            )

/** Class TaxPayerDuns for process data of model Matrix IC.
 *
 *  @constructor create a new TaxPayerDuns with parameters: taxpayerId and personalId and personalType and companyTradeName
 *  @param taxpayerId
 *  @param personalId
 *  @param personalType
 *  @param companyTradeName
 */
case class TaxPayerDuns(taxpayerId: String,
                        personalId: String,
                        personalType: String,
                        companyTradeName: String
                       )

/** Class Address for process data of model Matrix IC.
 *
 *  @constructor create a new Address with parameters: addressCountryId and addressDistrictId and addressLastChangeDate and noContactType and provinceId and residenceCountryId and urbanizationName and countryId and roadTypeId and positionDesc and additionalAddressDesc and phoneNumber1Id and addressDistrictName and roadId and addressStartDate and residenceType and additionalAddressDataDesc and mainAddressId and cutoffDate and audtiminsertDate and customerId and phoneType and addressEntryDate and effectiveEndDate and phone1Type and addressDesc and addressStatusType and effectiveStartDate and roadNumberId and zipcodeName and addressCustomerRelationType and registrationType and addressDepartmentId and mainAddressType and zipcodeId and entityId and phoneNumberId
 *  @param addressCountryId
 *  @param addressDistrictId
 *  @param addressLastChangeDate
 *  @param noContactType
 *  @param provinceId
 *  @param residenceCountryId
 *  @param urbanizationName
 *  @param countryId
 *  @param roadTypeId
 *  @param positionDesc
 *  @param additionalAddressDesc
 *  @param phoneNumber1Id
 *  @param addressDistrictName
 *  @param roadId
 *  @param addressStartDate
 *  @param residenceType
 *  @param additionalAddressDataDesc
 *  @param mainAddressId
 *  @param cutoffDate
 *  @param audtiminsertDate
 *  @param customerId
 *  @param phoneType
 *  @param addressEntryDate
 *  @param effectiveEndDate
 *  @param phone1Type
 *  @param addressDesc
 *  @param addressStatusType
 *  @param effectiveStartDate
 *  @param roadNumberId
 *  @param zipcodeName
 *  @param addressCustomerRelationType
 *  @param registrationType
 *  @param addressDepartmentId
 *  @param mainAddressType
 *  @param zipcodeId
 *  @param entityId
 *  @param phoneNumberId
 */
case class Address(addressCountryId: String,
                   addressDistrictId: String,
                   addressLastChangeDate: String,
                   noContactType: String,
                   provinceId: String,
                   residenceCountryId: String,
                   urbanizationName: String,
                   countryId: String,
                   roadTypeId: String,
                   positionDesc: String,
                   additionalAddressDesc: String,
                   phoneNumber1Id: String,
                   addressDistrictName: String,
                   roadId: String,
                   addressStartDate: String,
                   residenceType: String,
                   additionalAddressDataDesc: String,
                   mainAddressId: String,
                   cutoffDate: String,
                   audtiminsertDate: String,
                   customerId: String,
                   phoneType: String,
                   addressEntryDate: String,
                   effectiveEndDate: String,
                   phone1Type: String,
                   addressDesc: String,
                   addressStatusType: String,
                   effectiveStartDate: String,
                   roadNumberId: String,
                   zipcodeName: String,
                   addressCustomerRelationType: String,
                   registrationType: String,
                   addressDepartmentId: String,
                   mainAddressType: String,
                   zipcodeId: String,
                   entityId: String,
                   phoneNumberId: String
                  )

/** Class AddressContactability for process data of model Matrix IC.
 *
 *  @constructor create a new AddressContactability with parameters: urbanizationName and addressDistrictName and provinceName and audtiminsertDate and addressDistrictSunatName and additionalAddressDataDesc and dataSourceName and personalId and addressProvinceSunatName and additionalAddressDesc and orderLoadType and personalType and addressDepartmentName and addressDepartmentSunatName and cutoffDate and sunatAddressGeolocationId
 *  @param urbanizationName
 *  @param addressDistrictName
 *  @param provinceName
 *  @param audtiminsertDate
 *  @param addressDistrictSunatName
 *  @param additionalAddressDataDesc
 *  @param dataSourceName
 *  @param personalId
 *  @param addressProvinceSunatName
 *  @param additionalAddressDesc
 *  @param orderLoadType
 *  @param personalType
 *  @param addressDepartmentName
 *  @param addressDepartmentSunatName
 *  @param cutoffDate
 *  @param sunatAddressGeolocationId
 */
case class AddressContactability(urbanizationName: String,
                                 addressDistrictName: String,
                                 provinceName: String,
                                 audtiminsertDate: String,
                                 addressDistrictSunatName: String,
                                 additionalAddressDataDesc: String,
                                 dataSourceName: String,
                                 personalId: String,
                                 addressProvinceSunatName: String,
                                 additionalAddressDesc: String,
                                 orderLoadType: String,
                                 personalType: String,
                                 addressDepartmentName: String,
                                 addressDepartmentSunatName: String,
                                 cutoffDate: String,
                                 sunatAddressGeolocationId: String
                                )

/** Class Reniec for process data of model Matrix IC.
 *
 *  @constructor create a new Reniec with parameters: personalId and personalType and audtiminsertDate and voteGroupId and birthDate and cutoffDate and secondLastName and genderType and signatureType and customerName and addressDistrictName and lastName and voteGeolocationId and photoType and voteProvinceName and voteDepartmentName
 *  @param personalId
 *  @param personalType
 *  @param audtiminsertDate
 *  @param voteGroupId
 *  @param birthDate
 *  @param cutoffDate
 *  @param secondLastName
 *  @param genderType
 *  @param signatureType
 *  @param customerName
 *  @param addressDistrictName
 *  @param lastName
 *  @param voteGeolocationId
 *  @param photoType
 *  @param voteProvinceName
 *  @param voteDepartmentName
 */
case class Reniec(personalId: String,
                  personalType: String,
                  audtiminsertDate: String,
                  voteGroupId: String,
                  birthDate: String,
                  cutoffDate: String,
                  secondLastName: String,
                  genderType: String,
                  signatureType: String,
                  customerName: String,
                  addressDistrictName: String,
                  lastName: String,
                  voteGeolocationId: String,
                  photoType: String,
                  voteProvinceName: String,
                  voteDepartmentName: String
                 )

/** Class AccountSbsAggregate for process data of model Matrix IC.
 *
 *  @constructor create a new AccountSbsAggregate with parameters: productDefinerId and sbsCreditType and productGroupType and balanceRegisteredRccType and commercialProductId and commercialSubproductId and creditType and cutoffDate
 *  @param productDefinerId
 *  @param sbsCreditType
 *  @param productGroupType
 *  @param balanceRegisteredRccType
 *  @param commercialProductId
 *  @param commercialSubproductId
 *  @param creditType
 *  @param cutoffDate
 */
case class AccountSbsAggregate(productDefinerId: String,
                               sbsCreditType: String,
                               productGroupType: String,
                               balanceRegisteredRccType: String,
                               commercialProductId: String,
                               commercialSubproductId: String,
                               creditType: String,
                               cutoffDate: String
                              )

/** Class CodeSbsAggregate for process data of model Matrix IC.
 *
 *  @constructor create a new CodeSbsAggregate with parameters: sbsCustomerId and personalId and personalType
 *  @param sbsCustomerId
 *  @param personalId
 *  @param personalType
 */
case class CodeSbsAggregate(sbsCustomerId: String,
                            personalId: String,
                            personalType: String
                           )

/** Class RccBalance for process data of model Matrix IC.
 *
 *  @constructor create a new RccBalance with parameters: cutoffDate and sbsCustomerId and productDefinerId and balanceAmount and sbsEntityId and sbsCreditType
 *  @param cutoffDate
 *  @param sbsCustomerId
 *  @param productDefinerId
 *  @param balanceAmount
 *  @param sbsEntityId
 *  @param sbsCreditType
 */
case class RccBalance(cutoffDate: String,
                      sbsCustomerId: String,
                      productDefinerId: String,
                      balanceAmount: String,
                      sbsEntityId: String,
                      sbsCreditType: String
                     )

/** Class Entitys for process data of model Matrix IC.
 *
 *  @constructor create a new Entitys with parameters: fsEntIDBCP and fsEntIDIBK and fsEntIDSCO and fsEntIDBBVA and fsEntIDSAGA and fsEntIDFINANA and fsEntIDMIBCO and fsEntIDCJPIU and fsEntIDCJTRU and fsEntIDCJAQP and fsEntIDCJHYO and fsEntIDCJCUS and fsEntIDCJMAY and fsEntIDCJPIS and fsEntIDCJSULL and fsEntIDCJTAC and fsEntIDCJICA and fsEntIDBBVACF
 *  @param fsEntIDBCP
 *  @param fsEntIDIBK
 *  @param fsEntIDSCO
 *  @param fsEntIDBBVA
 *  @param fsEntIDSAGA
 *  @param fsEntIDFINANA
 *  @param fsEntIDMIBCO
 *  @param fsEntIDCJPIU
 *  @param fsEntIDCJTRU
 *  @param fsEntIDCJAQP
 *  @param fsEntIDCJHYO
 *  @param fsEntIDCJCUS
 *  @param fsEntIDCJMAY
 *  @param fsEntIDCJPIS
 *  @param fsEntIDCJSULL
 *  @param fsEntIDCJTAC
 *  @param fsEntIDCJICA
 *  @param fsEntIDBBVACF
 */
case class Entitys(fsEntIDBCP: String,
                   fsEntIDIBK: String,
                   fsEntIDSCO: String,
                   fsEntIDBBVA: String,
                   fsEntIDSAGA: String,
                   fsEntIDFINANA: String,
                   fsEntIDMIBCO: String,
                   fsEntIDCJPIU: String,
                   fsEntIDCJTRU: String,
                   fsEntIDCJAQP: String,
                   fsEntIDCJHYO: String,
                   fsEntIDCJCUS: String,
                   fsEntIDCJMAY: String,
                   fsEntIDCJPIS: String,
                   fsEntIDCJSULL: String,
                   fsEntIDCJTAC: String,
                   fsEntIDCJICA: String,
                   fsEntIDBBVACF: String
                  )

/** Class Contactability for process data of model Matrix IC.
 *
 *  @constructor create a new Contactability with parameters: personalId and personalType and cellphoneNumberId and cellphoneNumber1Id and cellphoneNumber2Id and cellphoneNumber3Id and cellphoneNumber4Id and cellphoneNumber5Id and landlineNumberId and landlineNumber1Id and landlineNumber2Id and landlineNumber3Id and landlineNumber4Id and landlineNumber5Id and emailDesc and email1Desc and email2Desc and cutoffDate
 *  @param personalId
 *  @param personalType
 *  @param cellphoneNumberId
 *  @param cellphoneNumber1Id
 *  @param cellphoneNumber2Id
 *  @param cellphoneNumber3Id
 *  @param cellphoneNumber4Id
 *  @param cellphoneNumber5Id
 *  @param landlineNumberId
 *  @param landlineNumber1Id
 *  @param landlineNumber2Id
 *  @param landlineNumber3Id
 *  @param landlineNumber4Id
 *  @param landlineNumber5Id
 *  @param emailDesc
 *  @param email1Desc
 *  @param email2Desc
 *  @param cutoffDate
 */
case class Contactability(personalId: String,
                  personalType: String,
                  cellphoneNumberId: String,
                  cellphoneNumber1Id: String,
                  cellphoneNumber2Id: String,
                  cellphoneNumber3Id: String,
                  cellphoneNumber4Id: String,
                  cellphoneNumber5Id: String,
                  landlineNumberId: String,
                  landlineNumber1Id: String,
                  landlineNumber2Id: String,
                  landlineNumber3Id: String,
                  landlineNumber4Id: String,
                  landlineNumber5Id: String,
                  emailDesc: String,
                  email1Desc: String,
                  email2Desc: String,
                  cutoffDate: String
                 )
/** Class ParameterFileIc for process data of model Matrix IC.
 *
 *  @constructor create a new ParameterFileIc with parameters: parameterDesc and parameterValue17Desc and parameterValue5Desc and parameterValue16Desc and parameterValue11Desc and parameterValue18Desc and parameterValue6Desc and parameterValue14Desc and parameterValue1Desc and parameterValueDesc and parameterValue3Desc and parameterValue13Desc and parameterId and cutoffDate and parameterValue12Desc and parameterValue2Desc and parameterValue10Desc and audtiminsertDate and executionProcessId and modificationUserId and parameterValue15Desc and parameterValue19Desc and parameterValue4Desc and parameterValue9Desc and parameterValue7Desc and parameterValue8Desc
 *  @param parameterDesc
 *  @param parameterValue17Desc
 *  @param parameterValue5Desc
 *  @param parameterValue16Desc
 *  @param parameterValue11Desc
 *  @param parameterValue18Desc
 *  @param parameterValue6Desc
 *  @param parameterValue14Desc
 *  @param parameterValue1Desc
 *  @param parameterValueDesc
 *  @param parameterValue3Desc
 *  @param parameterValue13Desc
 *  @param parameterId
 *  @param cutoffDate
 *  @param parameterValue12Desc
 *  @param parameterValue2Desc
 *  @param parameterValue10Desc
 *  @param audtiminsertDate
 *  @param executionProcessId
 *  @param modificationUserId
 *  @param parameterValue15Desc
 *  @param parameterValue19Desc
 *  @param parameterValue4Desc
 *  @param parameterValue9Desc
 *  @param parameterValue7Desc
 *  @param parameterValue8Desc
 */
case class ParameterFileIc(parameterDesc: String,
                           parameterValue17Desc: String,
                           parameterValue5Desc: String,
                           parameterValue16Desc: String,
                           parameterValue11Desc: String,
                           parameterValue18Desc: String,
                           parameterValue6Desc: String,
                           parameterValue14Desc: String,
                           parameterValue1Desc: String,
                           parameterValueDesc: String,
                           parameterValue3Desc: String,
                           parameterValue13Desc: String,
                           parameterId: String,
                           cutoffDate: String,
                           parameterValue12Desc: String,
                           parameterValue2Desc: String,
                           parameterValue10Desc: String,
                           audtiminsertDate: String,
                           executionProcessId: String,
                           modificationUserId: String,
                           parameterValue15Desc: String,
                           parameterValue19Desc: String,
                           parameterValue4Desc: String,
                           parameterValue9Desc: String,
                           parameterValue7Desc: String,
                           parameterValue8Desc: String
                          )

/** Class PeopleDaily for process data of model Matrix IC.
 *
 *  @constructor create a new PeopleDaily with parameters: employeeEntityId and accountingSubsectorId and robinsonType and spousePersonalId and scopeType and admissionDate and customerRegBranchId and residenceCountryId and persDataProtectType and subtypePersonalType and secondLastName and senderApplicationType and customerName and employeesNumber and customerStatusType and employeeTypeId and userAuditId and genderType and entityDefaultType and entityId and customerEducationId and customerEndDate and roleType and segmentId and bbvaShareholderType and companyEconomicActivity1Id and corporateSegmentId and customerType and flatOwnedDate and jobType and epaCustomerType and activityEndDate and audtiminsertDate and childDependantsNumber and companyName and customerCompanyName and effectiveStartDate and highRotationType and lastName and birthCountryId and companyEconomicActivityId and countryId and externalManagerId and jobClassId and mainBranchsfId and personalType and sectorId and adultDependantsNumber and birthTownName and companyJoinDate and customerCompanyCountryId and customerId and effectiveEndDate and lastChangeBranchId and maritalStatusType and provinceBirthName and seniorPosType and vipCustomerType and accountingSectorId and adultDependantsDate and birthDate and companyFoundationDate and countryNationalityId and customerEntryDate and cutoffDate and groupDefaultType and lastChangeOpeDate and ownerSiteType and registrationType and thirdPersonalId and arrivalCustomerId and bankClassificationId and cnoId and companyType and customerCountryId and emailDesc and jobClassDesc and legalProcType and robinsonDate and secondPersonalType and specialStatusId and maritalStatusId and preferenceLanguageId and updateCustomerType and personalId and secondPersonalId and sourceEntityId and thirdPersonalType and spousePersonalType and titleType
 *  @param employeeEntityId
 *  @param accountingSubsectorId
 *  @param robinsonType
 *  @param spousePersonalId
 *  @param scopeType
 *  @param admissionDate
 *  @param customerRegBranchId
 *  @param residenceCountryId
 *  @param persDataProtectType
 *  @param subtypePersonalType
 *  @param secondLastName
 *  @param senderApplicationType
 *  @param customerName
 *  @param employeesNumber
 *  @param customerStatusType
 *  @param employeeTypeId
 *  @param userAuditId
 *  @param genderType
 *  @param entityDefaultType
 *  @param entityId
 *  @param customerEducationId
 *  @param customerEndDate
 *  @param roleType
 *  @param segmentId
 *  @param bbvaShareholderType
 *  @param companyEconomicActivity1Id
 *  @param corporateSegmentId
 *  @param customerType
 *  @param flatOwnedDate
 *  @param jobType
 *  @param epaCustomerType
 *  @param activityEndDate
 *  @param audtiminsertDate
 *  @param childDependantsNumber
 *  @param companyName
 *  @param customerCompanyName
 *  @param effectiveStartDate
 *  @param highRotationType
 *  @param lastName
 *  @param birthCountryId
 *  @param companyEconomicActivityId
 *  @param countryId
 *  @param externalManagerId
 *  @param jobClassId
 *  @param mainBranchsfId
 *  @param personalType
 *  @param sectorId
 *  @param adultDependantsNumber
 *  @param birthTownName
 *  @param companyJoinDate
 *  @param customerCompanyCountryId
 *  @param customerId
 *  @param effectiveEndDate
 *  @param lastChangeBranchId
 *  @param maritalStatusType
 *  @param provinceBirthName
 *  @param seniorPosType
 *  @param vipCustomerType
 *  @param accountingSectorId
 *  @param adultDependantsDate
 *  @param birthDate
 *  @param companyFoundationDate
 *  @param countryNationalityId
 *  @param customerEntryDate
 *  @param cutoffDate
 *  @param groupDefaultType
 *  @param lastChangeOpeDate
 *  @param ownerSiteType
 *  @param registrationType
 *  @param thirdPersonalId
 *  @param arrivalCustomerId
 *  @param bankClassificationId
 *  @param cnoId
 *  @param companyType
 *  @param customerCountryId
 *  @param emailDesc
 *  @param jobClassDesc
 *  @param legalProcType
 *  @param robinsonDate
 *  @param secondPersonalType
 *  @param specialStatusId
 *  @param maritalStatusId
 *  @param preferenceLanguageId
 *  @param updateCustomerType
 *  @param personalId
 *  @param secondPersonalId
 *  @param sourceEntityId
 *  @param thirdPersonalType
 *  @param spousePersonalType
 *  @param titleType
 */
case class PeopleDaily(employeeEntityId: String,
                       accountingSubsectorId: String,
                       robinsonType: String,
                       spousePersonalId: String,
                       scopeType: String,
                       admissionDate: String,
                       customerRegBranchId: String,
                       residenceCountryId: String,
                       persDataProtectType: String,
                       subtypePersonalType: String,
                       secondLastName: String,
                       senderApplicationType: String,
                       customerName: String,
                       employeesNumber: String,
                       customerStatusType: String,
                       employeeTypeId: String,
                       userAuditId: String,
                       genderType: String,
                       entityDefaultType: String,
                       entityId: String,
                       customerEducationId: String,
                       customerEndDate: String,
                       roleType: String,
                       segmentId: String,
                       bbvaShareholderType: String,
                       companyEconomicActivity1Id: String,
                       corporateSegmentId: String,
                       customerType: String,
                       flatOwnedDate: String,
                       jobType: String,
                       epaCustomerType: String,
                       activityEndDate: String,
                       audtiminsertDate: String,
                       childDependantsNumber: String,
                       companyName: String,
                       customerCompanyName: String,
                       effectiveStartDate: String,
                       highRotationType: String,
                       lastName: String,
                       birthCountryId: String,
                       companyEconomicActivityId: String,
                       countryId: String,
                       externalManagerId: String,
                       jobClassId: String,
                       mainBranchsfId: String,
                       personalType: String,
                       sectorId: String,
                       adultDependantsNumber: String,
                       birthTownName: String,
                       companyJoinDate: String,
                       customerCompanyCountryId: String,
                       customerId: String,
                       effectiveEndDate: String,
                       lastChangeBranchId: String,
                       maritalStatusType: String,
                       provinceBirthName: String,
                       seniorPosType: String,
                       vipCustomerType: String,
                       accountingSectorId: String,
                       adultDependantsDate: String,
                       birthDate: String,
                       companyFoundationDate: String,
                       countryNationalityId: String,
                       customerEntryDate: String,
                       cutoffDate: String,
                       groupDefaultType: String,
                       lastChangeOpeDate: String,
                       ownerSiteType: String,
                       registrationType: String,
                       thirdPersonalId: String,
                       arrivalCustomerId: String,
                       bankClassificationId: String,
                       cnoId: String,
                       companyType: String,
                       customerCountryId: String,
                       emailDesc: String,
                       jobClassDesc: String,
                       legalProcType: String,
                       robinsonDate: String,
                       secondPersonalType: String,
                       specialStatusId: String,
                       maritalStatusId: String,
                       preferenceLanguageId: String,
                       updateCustomerType: String,
                       personalId: String,
                       secondPersonalId: String,
                       sourceEntityId: String,
                       thirdPersonalType: String,
                       spousePersonalType: String,
                       titleType: String
                      )

/** Class BalanceAggregateMonthly for process data of model Matrix IC.
 *
 *  @constructor create a new BalanceAggregateMonthly with parameters: monthAvgBalAssetAmount and audtiminsertDate and yOrdinaryMarginAmount and mFinancialMarginAmount and customerId and monthAvgOutOfBalanceAmount and mOrdinaryMarginAmount and monthAvgBalLiabilityAmount and currentContractId and commercialProductId and yoyFinancialMarginAmount and cutoffDate and yFinancialMarginAmount and yoyOrdinaryMarginAmount
 *  @param monthAvgBalAssetAmount
 *  @param audtiminsertDate
 *  @param yOrdinaryMarginAmount
 *  @param mFinancialMarginAmount
 *  @param customerId
 *  @param monthAvgOutOfBalanceAmount
 *  @param mOrdinaryMarginAmount
 *  @param monthAvgBalLiabilityAmount
 *  @param currentContractId
 *  @param commercialProductId
 *  @param yoyFinancialMarginAmount
 *  @param cutoffDate
 *  @param yFinancialMarginAmount
 *  @param yoyOrdinaryMarginAmount
 */
case class BalanceAggregateMonthly(monthAvgBalAssetAmount: String,
                                   audtiminsertDate: String,
                                   yOrdinaryMarginAmount: String,
                                   mFinancialMarginAmount: String,
                                   customerId: String,
                                   monthAvgOutOfBalanceAmount: String,
                                   mOrdinaryMarginAmount: String,
                                   monthAvgBalLiabilityAmount: String,
                                   currentContractId: String,
                                   commercialProductId: String,
                                   yoyFinancialMarginAmount: String,
                                   cutoffDate: String,
                                   yFinancialMarginAmount: String,
                                   yoyOrdinaryMarginAmount: String
                                  )

/** Class ProductSubproduct for process data of model Matrix IC.
 *
 *  @constructor create a new ProductSubproduct with parameters: cutoffDate and commercialProductId and audtiminsertDate and commercialProductDesc
 *  @param cutoffDate
 *  @param commercialProductId
 *  @param audtiminsertDate
 *  @param commercialProductDesc
 */
case class ProductSubproduct(cutoffDate: String,
                             commercialProductId: String,
                             audtiminsertDate: String,
                             commercialProductDesc: String
                            )



/** Class ProductLoanDaily for process data of model Matrix IC.
 *
 *  @constructor create a new ProductLoanDaily with parameters: dashboardType and fiscalType and loanClassType and contractingCampaignId and currencyBalanceAmount and decAdditionalInstlmntAmount and commercialSubproductId and dealerId and numDaysDefaultPaymentNumber and roleType and theoreticalBalanceAmount and customerType and ownerBranchId and monthlyInstallmentAmount and cutoffDate and zipcodeId and currencyId and installmentsUnpaidNumber and novAdditionalInstlmntAmount and provinceId and additionalAddressDesc and branchContractId and currentTaePer and mayAdditionalInstlmntAmount and addressDepartmentId and sepAdditionalInstlmntAmount and augAdditionalInstlmntAmount and contractingChannelId and dealerlId and marAdditionalInstlmntAmount and refinancingId and customerId and additionalInstallmentType and offlineIndicatorType and contractCancelDate and customerName and janAdditionalInstlmntAmount and pendingInstallmentsNumber and phoneNumberId and aprAdditionalInstlmntAmount and contractRegisterDate and currentContractId and febAdditionalInstlmntAmount and junAdditionalInstlmntAmount and nonPaymentTotalAmount and paymentId and audtiminsertDate and contractStatusType and currentExpiryDate and julAdditionalInstlmntAmount and nextRepaymentDate and octAdditionalInstlmntAmount and subjectiveStatusType and drawnAmount and gpEndId and personalId and installmentsPaidNumber and personalType and repaymentFreqId and phoneNumber1Id and settlementFreqId and totalInstallmentsNumber
 *  @param dashboardType
 *  @param fiscalType
 *  @param loanClassType
 *  @param contractingCampaignId
 *  @param currencyBalanceAmount
 *  @param decAdditionalInstlmntAmount
 *  @param commercialSubproductId
 *  @param dealerId
 *  @param numDaysDefaultPaymentNumber
 *  @param roleType
 *  @param theoreticalBalanceAmount
 *  @param customerType
 *  @param ownerBranchId
 *  @param monthlyInstallmentAmount
 *  @param cutoffDate
 *  @param zipcodeId
 *  @param currencyId
 *  @param installmentsUnpaidNumber
 *  @param novAdditionalInstlmntAmount
 *  @param provinceId
 *  @param additionalAddressDesc
 *  @param branchContractId
 *  @param currentTaePer
 *  @param mayAdditionalInstlmntAmount
 *  @param addressDepartmentId
 *  @param sepAdditionalInstlmntAmount
 *  @param augAdditionalInstlmntAmount
 *  @param contractingChannelId
 *  @param dealerlId
 *  @param marAdditionalInstlmntAmount
 *  @param refinancingId
 *  @param customerId
 *  @param additionalInstallmentType
 *  @param offlineIndicatorType
 *  @param contractCancelDate
 *  @param customerName
 *  @param janAdditionalInstlmntAmount
 *  @param pendingInstallmentsNumber
 *  @param phoneNumberId
 *  @param aprAdditionalInstlmntAmount
 *  @param contractRegisterDate
 *  @param currentContractId
 *  @param febAdditionalInstlmntAmount
 *  @param junAdditionalInstlmntAmount
 *  @param nonPaymentTotalAmount
 *  @param paymentId
 *  @param audtiminsertDate
 *  @param contractStatusType
 *  @param currentExpiryDate
 *  @param julAdditionalInstlmntAmount
 *  @param nextRepaymentDate
 *  @param octAdditionalInstlmntAmount
 *  @param subjectiveStatusType
 *  @param drawnAmount
 *  @param gpEndId
 *  @param personalId
 *  @param installmentsPaidNumber
 *  @param personalType
 *  @param repaymentFreqId
 *  @param phoneNumber1Id
 *  @param settlementFreqId
 *  @param totalInstallmentsNumber
 */
case class ProductLoanDaily(dashboardType: String,
                            fiscalType: String,
                            loanClassType: String,
                            contractingCampaignId: String,
                            currencyBalanceAmount: String,
                            decAdditionalInstlmntAmount: String,
                            commercialSubproductId: String,
                            dealerId: String,
                            numDaysDefaultPaymentNumber: String,
                            roleType: String,
                            theoreticalBalanceAmount: String,
                            customerType: String,
                            ownerBranchId: String,
                            monthlyInstallmentAmount: String,
                            cutoffDate: String,
                            zipcodeId: String,
                            currencyId: String,
                            installmentsUnpaidNumber: String,
                            novAdditionalInstlmntAmount: String,
                            provinceId: String,
                            additionalAddressDesc: String,
                            branchContractId: String,
                            currentTaePer: String,
                            mayAdditionalInstlmntAmount: String,
                            addressDepartmentId: String,
                            sepAdditionalInstlmntAmount: String,
                            augAdditionalInstlmntAmount: String,
                            contractingChannelId: String,
                            dealerlId: String,
                            marAdditionalInstlmntAmount: String,
                            refinancingId: String,
                            customerId: String,
                            additionalInstallmentType: String,
                            offlineIndicatorType: String,
                            contractCancelDate: String,
                            customerName: String,
                            janAdditionalInstlmntAmount: String,
                            pendingInstallmentsNumber: String,
                            phoneNumberId: String,
                            aprAdditionalInstlmntAmount: String,
                            contractRegisterDate: String,
                            currentContractId: String,
                            febAdditionalInstlmntAmount: String,
                            junAdditionalInstlmntAmount: String,
                            nonPaymentTotalAmount: String,
                            paymentId: String,
                            audtiminsertDate: String,
                            contractStatusType: String,
                            currentExpiryDate: String,
                            julAdditionalInstlmntAmount: String,
                            nextRepaymentDate: String,
                            octAdditionalInstlmntAmount: String,
                            subjectiveStatusType: String,
                            drawnAmount: String,
                            gpEndId: String,
                            personalId: String,
                            installmentsPaidNumber: String,
                            personalType: String,
                            repaymentFreqId: String,
                            phoneNumber1Id: String,
                            settlementFreqId: String,
                            totalInstallmentsNumber: String
                           )


/** Class Bin for process data of model Matrix IC.
 *
 *  @constructor create a new Bin with parameters: creditLimitCorrectionPer and businessCardType and currencyId and cardBicurrencyType and cardClassicVipType and deditAvailableType and cardDesc and cardBinNumber and cardType and freeField1Desc and businessCardBankType and cardForeignType and creditWithoutDebitType and creditAvailableType and sourceEntityId and affinityCardType and debitAvailableCorrectionPer and freeFieldDesc and cutoffDate and freeField2Desc and cardBinId and entityId and stampingDate and audtiminsertDate and directBillingType and cardAdministratorType and contractAvailableType and serviceId
 *  @param creditLimitCorrectionPer
 *  @param businessCardType
 *  @param currencyId
 *  @param cardBicurrencyType
 *  @param cardClassicVipType
 *  @param deditAvailableType
 *  @param cardDesc
 *  @param cardBinNumber
 *  @param cardType
 *  @param freeField1Desc
 *  @param businessCardBankType
 *  @param cardForeignType
 *  @param creditWithoutDebitType
 *  @param creditAvailableType
 *  @param sourceEntityId
 *  @param affinityCardType
 *  @param debitAvailableCorrectionPer
 *  @param freeFieldDesc
 *  @param cutoffDate
 *  @param freeField2Desc
 *  @param cardBinId
 *  @param entityId
 *  @param stampingDate
 *  @param audtiminsertDate
 *  @param directBillingType
 *  @param cardAdministratorType
 *  @param contractAvailableType
 *  @param serviceId
 */
case class Bin(creditLimitCorrectionPer: String,
               businessCardType: String,
               currencyId: String,
               cardBicurrencyType: String,
               cardClassicVipType: String,
               deditAvailableType: String,
               cardDesc: String,
               cardBinNumber: String,
               cardType: String,
               freeField1Desc: String,
               businessCardBankType: String,
               cardForeignType: String,
               creditWithoutDebitType: String,
               creditAvailableType: String,
               sourceEntityId: String,
               affinityCardType: String,
               debitAvailableCorrectionPer: String,
               freeFieldDesc: String,
               cutoffDate: String,
               freeField2Desc: String,
               cardBinId: String,
               entityId: String,
               stampingDate: String,
               audtiminsertDate: String,
               directBillingType: String,
               cardAdministratorType: String,
               contractAvailableType: String,
               serviceId: String
              )

/** Class BalanceMonthlyCorp for process data of model Matrix IC.
 *
 *  @constructor create a new BalanceMonthlyCorp with parameters: glAccountBranchId and managementProductId and recordsNumber and subrateCodeType and localManagerId and commercialProductId and ctvalCurrentBalanceAmount and groupDepositId and globalSegmentId and resultYearBalCtvalAmount and sourceApplicationId and avgInterannualBalLocalAmount and creatingServiceId and addressRoad1Name and countervalueCurrencyId and customerCountryId and cutoffDate and coapType and rateCodeType and entityId and resultMonthBalCtvalAmount and accountBreakdownId and typeDepositId and sourceCodeBalanceId and countryId and audtiminsertDate and commercialSubproductId and resultYoyBalCtvalAmount and avgMonthlyBalLocalAmount and customerId and currentContractId and customerEntityId and externalManagerId and glAccountEntityId and prefixManagementAccountType and avgYearlyBalLocalAmount and glAccountId and contractIdType
 *  @param glAccountBranchId
 *  @param managementProductId
 *  @param recordsNumber
 *  @param subrateCodeType
 *  @param localManagerId
 *  @param commercialProductId
 *  @param ctvalCurrentBalanceAmount
 *  @param groupDepositId
 *  @param globalSegmentId
 *  @param resultYearBalCtvalAmount
 *  @param sourceApplicationId
 *  @param avgInterannualBalLocalAmount
 *  @param creatingServiceId
 *  @param addressRoad1Name
 *  @param countervalueCurrencyId
 *  @param customerCountryId
 *  @param cutoffDate
 *  @param coapType
 *  @param rateCodeType
 *  @param entityId
 *  @param resultMonthBalCtvalAmount
 *  @param accountBreakdownId
 *  @param typeDepositId
 *  @param sourceCodeBalanceId
 *  @param countryId
 *  @param audtiminsertDate
 *  @param commercialSubproductId
 *  @param resultYoyBalCtvalAmount
 *  @param avgMonthlyBalLocalAmount
 *  @param customerId
 *  @param currentContractId
 *  @param customerEntityId
 *  @param externalManagerId
 *  @param glAccountEntityId
 *  @param prefixManagementAccountType
 *  @param avgYearlyBalLocalAmount
 *  @param glAccountId
 *  @param contractIdType
 */
case class BalanceMonthlyCorp(glAccountBranchId: String,
                              managementProductId: String,
                              recordsNumber: String,
                              subrateCodeType: String,
                              localManagerId: String,
                              commercialProductId: String,
                              ctvalCurrentBalanceAmount: String,
                              groupDepositId: String,
                              globalSegmentId: String,
                              resultYearBalCtvalAmount: String,
                              sourceApplicationId: String,
                              avgInterannualBalLocalAmount: String,
                              creatingServiceId: String,
                              addressRoad1Name: String,
                              countervalueCurrencyId: String,
                              customerCountryId: String,
                              cutoffDate: String,
                              coapType: String,
                              rateCodeType: String,
                              entityId: String,
                              resultMonthBalCtvalAmount: String,
                              accountBreakdownId: String,
                              typeDepositId: String,
                              sourceCodeBalanceId: String,
                              countryId: String,
                              audtiminsertDate: String,
                              commercialSubproductId: String,
                              resultYoyBalCtvalAmount: String,
                              avgMonthlyBalLocalAmount: String,
                              customerId: String,
                              currentContractId: String,
                              customerEntityId: String,
                              externalManagerId: String,
                              glAccountEntityId: String,
                              prefixManagementAccountType: String,
                              avgYearlyBalLocalAmount: String,
                              glAccountId: String,
                              contractIdType: String
                             )

/** Class ContractMaster for process data of model Matrix IC.
 *
 *  @constructor create a new ContractMaster with parameters: aplCreditInterestPer and applicationId and contractId and countryId and customerId and sourceEntityId and customerCountryId and sourceBranchId and benchmarkDaysTermNumber and channelId and counterpartId and dependentId and rarFamilyId and baselCategoryId and contractCancelDate and cutoffDate and epigBranchCustomerAxisType and fundRefinancingType and interestToApplyPer and pageId and spreadPer and audtiminsertDate and refinancingEffectiveDate and commercialProductId and irregularInvestmentNumber and commercialSituationId and contractRegisterDate and contractType and destinationId and failedContractDate and glAccountDefaultEntryDate and inventoriesSourceType and currencyId and currentContractId and currentExpiryDate and securitizationId and currentFirstNonPaymentDate and entityId and financialProductId and glAccountId and internalDepositRateId and refinancingContractType and senderApplicationId and defaultSituationComType and entryDefaultDate and firstDefaultEntryDate and internalDepositNumber and offerId and subdestinationId and finalInterestPer and interestSpreadPer and lastRenewalDate and lastRevisionDate and movToNonPerformingDate and vpoType and operationalStatusId and referenceMainInterestId and securitizationIssueDate and securitizedType and subrogatedType and variableRateReviewDate
 *  @param aplCreditInterestPer
 *  @param applicationId
 *  @param contractId
 *  @param countryId
 *  @param customerId
 *  @param sourceEntityId
 *  @param customerCountryId
 *  @param sourceBranchId
 *  @param benchmarkDaysTermNumber
 *  @param channelId
 *  @param counterpartId
 *  @param dependentId
 *  @param rarFamilyId
 *  @param baselCategoryId
 *  @param contractCancelDate
 *  @param cutoffDate
 *  @param epigBranchCustomerAxisType
 *  @param fundRefinancingType
 *  @param interestToApplyPer
 *  @param pageId
 *  @param spreadPer
 *  @param audtiminsertDate
 *  @param refinancingEffectiveDate
 *  @param commercialProductId
 *  @param irregularInvestmentNumber
 *  @param commercialSituationId
 *  @param contractRegisterDate
 *  @param contractType
 *  @param destinationId
 *  @param failedContractDate
 *  @param glAccountDefaultEntryDate
 *  @param inventoriesSourceType
 *  @param currencyId
 *  @param currentContractId
 *  @param currentExpiryDate
 *  @param securitizationId
 *  @param currentFirstNonPaymentDate
 *  @param entityId
 *  @param financialProductId
 *  @param glAccountId
 *  @param internalDepositRateId
 *  @param refinancingContractType
 *  @param senderApplicationId
 *  @param defaultSituationComType
 *  @param entryDefaultDate
 *  @param firstDefaultEntryDate
 *  @param internalDepositNumber
 *  @param offerId
 *  @param subdestinationId
 *  @param finalInterestPer
 *  @param interestSpreadPer
 *  @param lastRenewalDate
 *  @param lastRevisionDate
 *  @param movToNonPerformingDate
 *  @param vpoType
 *  @param operationalStatusId
 *  @param referenceMainInterestId
 *  @param securitizationIssueDate
 *  @param securitizedType
 *  @param subrogatedType
 *  @param variableRateReviewDate
 */
case class ContractMaster(aplCreditInterestPer: String,
                          applicationId: String,
                          contractId: String,
                          countryId: String,
                          customerId: String,
                          sourceEntityId: String,
                          customerCountryId: String,
                          sourceBranchId: String,
                          benchmarkDaysTermNumber: String,
                          channelId: String,
                          counterpartId: String,
                          dependentId: String,
                          rarFamilyId: String,
                          baselCategoryId: String,
                          contractCancelDate: String,
                          cutoffDate: String,
                          epigBranchCustomerAxisType: String,
                          fundRefinancingType: String,
                          interestToApplyPer: String,
                          pageId: String,
                          spreadPer: String,
                          audtiminsertDate: String,
                          refinancingEffectiveDate: String,
                          commercialProductId: String,
                          irregularInvestmentNumber: String,
                          commercialSituationId: String,
                          contractRegisterDate: String,
                          contractType: String,
                          destinationId: String,
                          failedContractDate: String,
                          glAccountDefaultEntryDate: String,
                          inventoriesSourceType: String,
                          currencyId: String,
                          currentContractId: String,
                          currentExpiryDate: String,
                          securitizationId: String,
                          currentFirstNonPaymentDate: String,
                          entityId: String,
                          financialProductId: String,
                          glAccountId: String,
                          internalDepositRateId: String,
                          refinancingContractType: String,
                          senderApplicationId: String,
                          defaultSituationComType: String,
                          entryDefaultDate: String,
                          firstDefaultEntryDate: String,
                          internalDepositNumber: String,
                          offerId: String,
                          subdestinationId: String,
                          finalInterestPer: String,
                          interestSpreadPer: String,
                          lastRenewalDate: String,
                          lastRevisionDate: String,
                          movToNonPerformingDate: String,
                          vpoType: String,
                          operationalStatusId: String,
                          referenceMainInterestId: String,
                          securitizationIssueDate: String,
                          securitizedType: String,
                          subrogatedType: String,
                          variableRateReviewDate: String
                         )
