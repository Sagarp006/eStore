package com.shruteekatech.electronicstore.dtos;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
@MappedSuperclass
@Getter
@Setter
public class SuperEntityDto implements Serializable {
        private LocalDate createDate;
        private String createdBy;

        private LocalDate updateDate;
        private String updatedBy;

    }
