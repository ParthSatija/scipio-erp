import org.ofbiz.entity.util.*;

facilityData = context.facilityData ?: [:];

facilityId = facilityData.facilityId;
partyId = context.partyId;
facilities = null;
if (partyId) {
    facilities = delegator.findByAnd("Facility", ["ownerPartyId":partyId], null, false);
}
context.facilities = facilities;
context.facilityId = facilityId;

// SPECIAL: ProductStore.inventoryFacilityId could have weird config and not be in above list
storeInventoryFacilityOk = true;
inventoryFacilityId = context.productStore?.inventoryFacilityId;
inventoryFacility = null;
if (inventoryFacilityId) {
    storeInventoryFacilityOk = false;
    if (facilities) {
        for(fac in facilities) {
            if (fac.facilityId == inventoryFacilityId) {
                storeInventoryFacilityOk = true;
                inventoryFacility = fac;
                break;
            }
        }
    }
    if (!inventoryFacility) {
        inventoryFacility = delegator.findOne("Facility", [facilityId:inventoryFacilityId], false);
    }
}
context.storeInventoryFacilityOk = storeInventoryFacilityOk;
context.inventoryFacility = inventoryFacility;

context.productStoreFacilityMissing = (context.productStore && !inventoryFacilityId);

// SPECIAL: if there's no productStore yet, we transfer the facility into parameters.inventoryFacilityId
// so that the newly created one will always be preselected
if (!productStore && !parameters.inventoryFacilityId) {
    parameters.inventoryFacilityId = facilityId;
}
