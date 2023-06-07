package com.shruteekatech.electronicstore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public class SuperEntity implements Serializable {
    @CreationTimestamp
    @Column(name = "create_Date")
    private LocalDate createDate;
    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;
    @UpdateTimestamp
    @Column(name = "update_Date")
    private LocalDate updateDate;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

}
