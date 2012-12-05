/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.webevo.logic.similarity.textbased;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.EncoderException;

/**
 *
 * @author Jelena
 */
public class JecaVojiDojeta {

    public static void main(String[] args) {
        try {
            List<String> flist = Arrays.asList("amountOfThisGood", "availabilityEnds", "availabilityStarts", "billingIncrement", "category", "closes", "color", "condition", "datatypeProductOrServiceProperty", "description", "durationOfWarrantyInMonths", "eligibleRegions", "hasCurrency", "hasCurrencyValue", "hasDUNS", "hasEAN_UCC-13", "hasGlobalLocationNumber", "hasGTIN-14", "hasGTIN-8", "hasISICv4", "hasMaxCurrencyValue", "hasMaxValue", "hasMaxValueFloat", "hasMaxValueInteger", "hasMinCurrencyValue", "hasMinValue", "hasMinValueFloat", "hasMinValueInteger", "hasMPN", "hasNAICS", "hasStockKeepingUnit", "hasUnitOfMeasurement", "hasValue", "hasValueFloat", "hasValueInteger", "legalName", "name", "opens", "priceType", "serialNumber", "taxID", "validThrough", "valueAddedTaxIncluded", "vatID", "acceptedPaymentMethods", "addOn", "advanceBookingRequirement", "appliesToDeliveryMethod", "appliesToPaymentMethod", "availableAtOrFrom", "availableDeliveryMethods", "deliveryLeadTime", "depth", "eligibleCustomerTypes", "eligibleDuration", "eligibleTransactionVolume", "equal", "greater", "greaterOrEqual", "hasBrand", "hasBusinessFunction", "hasEligibleQuantity", "hasInventoryLevel", "hasMakeAndModel", "hasManufacturer", "hasNext", "hasOpeningHoursDayOfWeek", "hasOpeningHoursSpecification", "hasPOS", "hasPrevious", "hasPriceSpecification", "hasWarrantyPromise", "hasWarrantyScope", "height", "includes", "includesObject", "isAccessoryOrSparePartFor", "isConsumableFor", "isSimilarTo", "isVariantOf", "lesser", "lesserOrEqual", "nonEqual", "offers", "owns", "predecessorOf", "qualitativeProductOrServiceProperty", "quantitativeProductOrServiceProperty", "seeks", "successorOf", "typeOfGood", "valueReference", "weight");
            List<String> slist = Arrays.asList("aggregateRating", "brand", "color", "depth", "gtin13", "gtin14", "gtin8", "height", "isAccessoryOrSparePartFor", "isConsumableFor", "thing", "isSimilarTo", "itemCondition", "logo", "manufacturer", "model", "mpn", "offers", "productID", "releaseDate", "review", "reviews", "sku", "weight", "width", "eligibleQuantity", "eligibleTransactionVolume", "maxPrice", "minPrice", "price", "priceCurrency", "validFrom", "validThrough", "valueAddedTaxIncluded", "acceptedPaymentMethod", "addOn", "advanceBookingRequirement", "aggregateRating", "availability", "availabilityEnds", "availabilityStarts", "availableDeliveryMethod", "businessFunction", "category", "deliveryLeadTime", "eligibleCustomerType", "eligibleDuration", "eligibleQuantity", "eligibleRegion", "eligibleTransactionVolume", "includesObject", "inventoryLevel", "itemCondition", "itemOffered", "mpn", "price", "priceCurrency", "priceSpecification", "priceValidUntil", "review", "reviews", "seller", "serialNumber", "sku", "validFrom", "validThrough", "warranty", "highPrice", "lowPrice", "offerCount", "isVariantOf", "predecessorOf", "successorOf", "amountOfThisGood", "businessFunction", "typeOfGood", "unitCode", "closes", "dayOfWeek", "opens", "validFrom", "validThrough", "billingIncrement", "priceType", "unitCode", "appliesToDeliveryMethod", "appliesToPaymentMethod", "additionalType", "description", "image", "name", "url", "description");

            String finalResult="";
            for (int i = 0; i < slist.size(); i++) {
                String secondword = slist.get(i);
                String firstword = flist.get(i);

                System.out.println(firstword+"-"+secondword);
                String result = new String();
                if (firstword.startsWith("has")|| firstword.startsWith("is") || firstword.startsWith("set") || firstword.startsWith("get")|| (firstword.length() >= secondword.length()&&(!secondword.startsWith("has")||!secondword.startsWith("is")||!secondword.startsWith("set")||!secondword.startsWith("get")) ) ){
                    System.out.println("SECOND");
                    
                    
                    result += LevensteinDistance.computeDistance(secondword, firstword) + ";";
                    result += (LevensteinDistance.levensteinDistanceWrapper(secondword, firstword) + ";");
                    result += (SoundexImplementation.soundexApache(secondword, firstword) + ";");
                    result += (SoundexImplementation.soundexApacheWrapper(secondword, firstword) + ";");
                    result += (SequenceSimilarity.sequenceSimilarity(secondword, firstword) + "\n");
                    System.out.println(result);

                } else {
                    System.out.println("FIRST");
                    result += LevensteinDistance.computeDistance(firstword, secondword) + ";";
                    result += (LevensteinDistance.levensteinDistanceWrapper(firstword, secondword) + ";");
                    result += (SoundexImplementation.soundexApache(firstword, secondword) + ";");
                    result += (SoundexImplementation.soundexApacheWrapper(firstword, secondword) + ";");
                    result += (SequenceSimilarity.sequenceSimilarity(firstword, secondword) + "\n");
                    System.out.println(result);
                }
                
                finalResult+=result;
            }
            System.out.println("FINAL:");
            System.out.println(finalResult);
        } catch (EncoderException ex) {
            Logger.getLogger(JecaVojiDojeta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
