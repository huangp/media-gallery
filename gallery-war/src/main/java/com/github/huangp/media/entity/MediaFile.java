package com.github.huangp.media.entity;

import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.github.huangp.media.model.MediaFileType;

/**
 * @author Patrick Huang
 *         <a href="mailto:pahuang@redhat.com">pahuang@redhat.com</a>
 */
@Entity
@Access(AccessType.FIELD)
public class MediaFile {
    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long version;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private MediaFileType fileType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date mediaCreationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastChanged;
}
