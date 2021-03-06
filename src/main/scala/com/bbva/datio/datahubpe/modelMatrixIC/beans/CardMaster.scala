package com.bbva.datio.datahubpe.modelMatrixIC.beans

/** Class CardMaster for process data of model Matrix IC.
 *
 *  @constructor create a new CardMaster with parameters: map and String]
 *  @param map
 *  @param String]
 */
class CardMaster (map: java.util.HashMap[String, String]){
  val pinRequestSequenceNumber: String = map.get("pinRequestSequenceNumber")
  val dailyBranchOperationNumber: String = map.get("dailyBranchOperationNumber")
  val auditDate: String = map.get("auditDate")
  val freeField3Desc: String = map.get("freeField3Desc")
  val pinAttemptsNumber: String = map.get("pinAttemptsNumber")
  val monthlyTransferLimitAmount: String = map.get("monthlyTransferLimitAmount")
  val campaignNumber: String = map.get("campaignNumber")
  val lastPosUseDate: String = map.get("lastPosUseDate")
  val cutoffDate: String = map.get("cutoffDate")
  val commercialSubproductId: String = map.get("commercialSubproductId")
  val keyCardHolderPersonalId: String = map.get("keyCardHolderPersonalId")
  val remittanceDate: String = map.get("remittanceDate")
  val freeField1Number: String = map.get("freeField1Number")
  val cardAssociatedAccountsNumber: String = map.get("cardAssociatedAccountsNumber")
  val collectionDate: String = map.get("collectionDate")
  val automaticIncidentSolutionType: String = map.get("automaticIncidentSolutionType")
  val pinRequestUserId: String = map.get("pinRequestUserId")
  val mandatoryReferenceType: String = map.get("mandatoryReferenceType")
  val freeFieldAmount: String = map.get("freeFieldAmount")
  val cardBonusId: String = map.get("cardBonusId")
  val languageId: String = map.get("languageId")
  val campaignId: String = map.get("campaignId")
  val effectiveStartDate: String = map.get("effectiveStartDate")
  val dailyTransferNumber: String = map.get("dailyTransferNumber")
  val monthlyTransferAmount: String = map.get("monthlyTransferAmount")
  val branchMonthlyAvailableAmount: String = map.get("branchMonthlyAvailableAmount")
  val cardAssociatedLinesNumber: String = map.get("cardAssociatedLinesNumber")
  val cardHolderName: String = map.get("cardHolderName")
  val dailyPinAttemptsNumber: String = map.get("dailyPinAttemptsNumber")
  val installmentCollectionAmount: String = map.get("installmentCollectionAmount")
  val serviceId: String = map.get("serviceId")
  val freeFieldDesc: String = map.get("freeFieldDesc")
  val paymentDueLockoutType: String = map.get("paymentDueLockoutType")
  val pvvHmsDate: String = map.get("pvvHmsDate")
  val atmMonthlyAvailableAmount: String = map.get("atmMonthlyAvailableAmount")
  val bonusExpiryDate: String = map.get("bonusExpiryDate")
  val monthlyCashAdvanceAmount: String = map.get("monthlyCashAdvanceAmount")
  val freeField2Number: String = map.get("freeField2Number")
  val participationOrderNumber: String = map.get("participationOrderNumber")
  val cardEndReasonId: String = map.get("cardEndReasonId")
  val cardRenewalType: String = map.get("cardRenewalType")
  val contactEmailVerifiedType: String = map.get("contactEmailVerifiedType")
  val extAtmMonthlyRefusalNumber: String = map.get("extAtmMonthlyRefusalNumber")
  val freeField1Desc: String = map.get("freeField1Desc")
  val atmDailyAvailableAmount: String = map.get("atmDailyAvailableAmount")
  val freeField5Desc: String = map.get("freeField5Desc")
  val pinOffsetNumber: String = map.get("pinOffsetNumber")
  val authorizationType: String = map.get("authorizationType")
  val cardChangeStatusDate: String = map.get("cardChangeStatusDate")
  val contractEndDate: String = map.get("contractEndDate")
  val dailyAtmOperationNumber: String = map.get("dailyAtmOperationNumber")
  val externalAtmRefusalDate: String = map.get("externalAtmRefusalDate")
  val internalAtmDailyLimitAmount: String = map.get("internalAtmDailyLimitAmount")
  val internalPosServiceType: String = map.get("internalPosServiceType")
  val ownBranchMonthlyLimitAmount: String = map.get("ownBranchMonthlyLimitAmount")
  val recordingDate: String = map.get("recordingDate")
  val cardBlockType: String = map.get("cardBlockType")
  val cardStatusType: String = map.get("cardStatusType")
  val dscrtyDataRecordingType: String = map.get("dscrtyDataRecordingType")
  val externalAtmServiceType: String = map.get("externalAtmServiceType")
  val freeField2Desc: String = map.get("freeField2Desc")
  val installmentCollectionId: String = map.get("installmentCollectionId")
  val lastReplacementUserId: String = map.get("lastReplacementUserId")
  val ownAtmMonthlyRefusalNumber: String = map.get("ownAtmMonthlyRefusalNumber")
  val pinAttemptsControlDate: String = map.get("pinAttemptsControlDate")
  val audtiminsertDate: String = map.get("audtiminsertDate")
  val cardPrintingReasonType: String = map.get("cardPrintingReasonType")
  val chipType: String = map.get("chipType")
  val dailyTransferLimitAmount: String = map.get("dailyTransferLimitAmount")
  val externalBranchServiceType: String = map.get("externalBranchServiceType")
  val freeFieldNumber: String = map.get("freeFieldNumber")
  val internalAtmServiceType: String = map.get("internalAtmServiceType")
  val lastPinReplacementDate: String = map.get("lastPinReplacementDate")
  val ownAtmDailyRefusalNumber: String = map.get("ownAtmDailyRefusalNumber")
  val branchId: String = map.get("branchId")
  val cardReceptionDate: String = map.get("cardReceptionDate")
  val currentContractId: String = map.get("currentContractId")
  val dailyPosOperationNumber: String = map.get("dailyPosOperationNumber")
  val externalPosServiceType: String = map.get("externalPosServiceType")
  val internalBranchServiceType: String = map.get("internalBranchServiceType")
  val lastAtmUseDate: String = map.get("lastAtmUseDate")
  val previousPanId: String = map.get("previousPanId")
  val cardBinNumber: String = map.get("cardBinNumber")
  val glAccountContractCancelDate: String = map.get("glAccountContractCancelDate")
  val cardDrawnAmount: String = map.get("cardDrawnAmount")
  val cardRenewalExpiryDate: String = map.get("cardRenewalExpiryDate")
  val contractRegisterDate: String = map.get("contractRegisterDate")
  val dailyTransferAmount: String = map.get("dailyTransferAmount")
  val graceMonthsNumber: String = map.get("graceMonthsNumber")
  val cardPhotoType: String = map.get("cardPhotoType")
  val currentExpiryDate: String = map.get("currentExpiryDate")
  val extAtmDailyRefusalNumber: String = map.get("extAtmDailyRefusalNumber")
  val freeField1Amount: String = map.get("freeField1Amount")
  val freeField4Desc: String = map.get("freeField4Desc")
  val lastBranchUseDate: String = map.get("lastBranchUseDate")
  val managerEmployeeId: String = map.get("managerEmployeeId")
  val onlinePurchaseType: String = map.get("onlinePurchaseType")
  val panId: String = map.get("panId")
  val cardReprintingReasonType: String = map.get("cardReprintingReasonType")
  val posMonthlyAvailableAmount: String = map.get("posMonthlyAvailableAmount")
  val creditLimitAmount: String = map.get("creditLimitAmount")
  val emergencyPrintingType: String = map.get("emergencyPrintingType")
  val noRenewalReasonId: String = map.get("noRenewalReasonId")
  val groupDepositId: String = map.get("groupDepositId")
  val monthlyTransferNumber: String = map.get("monthlyTransferNumber")
  val remittanceId: String = map.get("remittanceId")
  val installmentCollectionType: String = map.get("installmentCollectionType")
  val internalPosMonthLimitAmount: String = map.get("internalPosMonthLimitAmount")
  val overdraftLockoutType: String = map.get("overdraftLockoutType")
  val pinReactivationsNumber: String = map.get("pinReactivationsNumber")
  val rateCodeType: String = map.get("rateCodeType")
  val renewalCollectionType: String = map.get("renewalCollectionType")
  val totalCardId: String = map.get("totalCardId")
  val yearInstallmentCollectionId: String = map.get("yearInstallmentCollectionId")
  val internalAtmRefusalDate: String = map.get("internalAtmRefusalDate")
  val visaphoneNumber: String = map.get("visaphoneNumber")
  val internalPosDailyLimitAmount: String = map.get("internalPosDailyLimitAmount")
  val urgentPrintingType: String = map.get("urgentPrintingType")
  val manteinanceFeeMonthType: String = map.get("manteinanceFeeMonthType")
  val ownBranchDailyLimitAmount: String = map.get("ownBranchDailyLimitAmount")
  val participantType: String = map.get("participantType")
  val posDailyAvailableAmount: String = map.get("posDailyAvailableAmount")
  val preventiveBlockType: String = map.get("preventiveBlockType")
  val recordingRequestDate: String = map.get("recordingRequestDate")
  val terminalId: String = map.get("terminalId")
  val withdrawalLimitAtmAmount: String = map.get("withdrawalLimitAtmAmount")
  val previousOffsetPinNumber: String = map.get("previousOffsetPinNumber")
  val statementDebitType: String = map.get("statementDebitType")
  val printInstlmntCollectionType: String = map.get("printInstlmntCollectionType")
  val recordingLimitType: String = map.get("recordingLimitType")
  val recordingSourceId: String = map.get("recordingSourceId")
  val verifiedVisaAffiliationDate: String = map.get("verifiedVisaAffiliationDate")
  val renewalPeriodNumber: String = map.get("renewalPeriodNumber")
  val sempCommunicationDate: String = map.get("sempCommunicationDate")
  val verifiedVisaDisaffiliatedDate: String = map.get("verifiedVisaDisaffiliatedDate")
  val prefixId: String = map.get("prefixId")

}
