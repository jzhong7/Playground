//package com.company.K2;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
//public class Validate {
//    public String lengthOfLongestSubstring(String s) {
//        int[] index = new int[128];
//        int max = 0;
//        int lo = 0;
//        int hi = 0;
//        int start = -1;
//
//        while (hi < s.length()) {
//            lo = Math.max(index[s.charAt(hi)], lo);
////            max = Math.max(res, hi - lo + 1);
//            if (max < hi - lo + 1) {
//                start = lo;
//            }
//            index[s.charAt(hi)] = hi + 1;
//            hi++;
//        }
//
//        return s.substring(start, start + max);
//    }
//
//    public int[] sort(int[] nums) {
//
//        int lo = 0;
//        int hi = nums.length - 1;
//
//        while (lo < hi) {
//            if (nums[lo] )
//        }
//
//        return nums;
//    }
//    private boolean validateLicenses(List<Device> devices, List<License> existingLicenses) {
//        devices.stream().forEach(d -> {
//                Optional<License> existingLicenses = existingLicenses.stream()
//                        .filter(l -> l.isActive)
//                        .filter(l -> l.accountId.equals(d.accountId))
//                        .filter(l -> l.deviceType.equals(d.deviceType))
//                        .filter(l -> l.useCount < l.totalUsesAllowed)
//                        .findFirst();
//
////perform increment
//        if(existingLicense.isPresent()) {
//            existingLicense.get().useCount++;
//        }
//        else {
//            //Empty object?
//            return false;
//        }
//});
//
//        return true;
//    }
//
//
///*    private boolean validateLicenses(List<Device> devices, List<License> existingLicenses) {
//        List<License> licenses = new ArrayList();
//        // add each new devices into licenses
//        devices.stream().forEach(d ->{
//            Boolean flag = false;
//            for(License i : licenses) {
//                if(i.accountId.equals(d.accountId) && i.deviceType.equals(d.deviceType)) {
//                    flag = true;
//                    i.useCount++;
//                }
//            }
//            if(!flag) {
//                License lic = new License();
//                lic .accountId = d.accountId;
//                lic.deviceType = d.deviceType;
//                lic.useCount = getUseCount(d.accountId, d.deviceType, existingLicenses) + 1;
//                licenses.add(lic);
//            }
//
//        });
//
//        //check for each license of licenses
//        AtomicReference<Boolean> validate = new AtomicReference<>(true);
//        licenses.stream().forEach( i -> {
//            existingLicenses.stream().forEach(lic -> {
//                Boolean flag = false;
//                if(lic.accountId.equals(i.accountId)
//                        && lic.deviceType.equals(i.deviceType)
//                        && lic.useCount < i.totalUsesAllowed
//                        && i.isActive) {
//                    flag = true;
//                }
//                if (!flag) validate.set(false);
//            });
//        });
//
//        return validate.get();
//    }
//
//    private int getUseCount(String id, String type, List<License> existingLicense) {
//        return existingLicense.stream().filter(license -> license.accountId.equals(id)
//                && license.deviceType.equals(type)).findFirst().get().useCount;
//    }*/
//
////    private boolean validateLicenses(List<Device> devices, List<License> existingLicenses) {
////        List<License> licenses = new LinkedList<>();
////        devices.stream().forEach(d -> {
////            Boolean flag = false;
////            for (License l : licenses) {
////                if (l.accountId.equals(d.accountId)
////                        && l.deviceType.equals(d.deviceType)) {
////                    flag = true;
////                    l.useCount++;
////                }
////            }
////
////            if (!flag) {
////                License lic = new License();
////                lic.accountId = d.accountId;
////                lic.deviceType = d.deviceType;
////                lic.useCount = 1;
////                licenses.add(lic);
////            }
////        });
////        return false;
////    }
//
//}
