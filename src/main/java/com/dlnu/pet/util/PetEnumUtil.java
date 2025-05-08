package com.dlnu.pet.util;

/**
 * 宠物相关枚举值转换工具类
 */
public class PetEnumUtil {
    
    /**
     * 品种枚举
     */
    public enum Breed {
        CAT(0, "猫"),
        DOG(1, "狗"),
        OTHER(2, "其他");
        
        private final int code;
        private final String desc;
        
        Breed(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getDesc() {
            return desc;
        }
        
        public static String getDescByCode(int code) {
            for (Breed breed : values()) {
                if (breed.getCode() == code) {
                    return breed.getDesc();
                }
            }
            return "未知";
        }
        
        public static int getCodeByDesc(String desc) {
            for (Breed breed : values()) {
                if (breed.getDesc().equals(desc)) {
                    return breed.getCode();
                }
            }
            return -1;
        }
    }
    
    /**
     * 性别枚举
     */
    public enum Gender {
        MALE(0, "公"),
        FEMALE(1, "母");
        
        private final int code;
        private final String desc;
        
        Gender(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getDesc() {
            return desc;
        }
        
        public static String getDescByCode(int code) {
            for (Gender gender : values()) {
                if (gender.getCode() == code) {
                    return gender.getDesc();
                }
            }
            return "未知";
        }
        
        public static int getCodeByDesc(String desc) {
            for (Gender gender : values()) {
                if (gender.getDesc().equals(desc)) {
                    return gender.getCode();
                }
            }
            return -1;
        }
    }
    
    /**
     * 宠物状态枚举
     */
    public enum PetStatus {
        HOME(0, "家养"),
        STRAY(1, "流浪");
        
        private final int code;
        private final String desc;
        
        PetStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getDesc() {
            return desc;
        }
        
        public static String getDescByCode(int code) {
            for (PetStatus status : values()) {
                if (status.getCode() == code) {
                    return status.getDesc();
                }
            }
            return "未知";
        }
        
        public static int getCodeByDesc(String desc) {
            for (PetStatus status : values()) {
                if (status.getDesc().equals(desc)) {
                    return status.getCode();
                }
            }
            return -1;
        }
    }
    
    /**
     * 领养信息状态枚举
     */
    public enum AdoptionStatus {
        HOME(0, "家养"),
        PUBLISH(1, "发布领养"),
        WAITING(2, "待领养"),
        SUCCESS(3, "领养成功"),
        FAIL(4, "领养失败");
        
        private final int code;
        private final String desc;
        
        AdoptionStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        public int getCode() {
            return code;
        }
        
        public String getDesc() {
            return desc;
        }
        
        public static String getDescByCode(int code) {
            for (AdoptionStatus status : values()) {
                if (status.getCode() == code) {
                    return status.getDesc();
                }
            }
            return "未知";
        }
        
        public static int getCodeByDesc(String desc) {
            for (AdoptionStatus status : values()) {
                if (status.getDesc().equals(desc)) {
                    return status.getCode();
                }
            }
            return -1;
        }
    }
} 