    package com.ssafy.umzip.domain.delivery.dto;

    import lombok.*;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public class UserDeliveryMappingRepoDto {
        private Long deliveryId;
        private Long mappingId;
        private String detail;
        private Long codeSmallId;
        private Long companyId;
        private String companyName;
        private String imageUrl;
        private Long price;
        private Long reissuing;


        public UserDeliveryMappingRepoDto(Long deliveryId, Long mappingId, String detail, Long codeSmallId, Long companyId, String companyName, String imageUrl, Long price, Long reissuing) {
            this.deliveryId = deliveryId;
            this.mappingId = mappingId;
            this.detail = detail;
            this.codeSmallId = codeSmallId;
            this.companyId = companyId;
            this.companyName = companyName;
            this.imageUrl = imageUrl;
            this.price = price;
            this.reissuing = reissuing;
        }
    }
