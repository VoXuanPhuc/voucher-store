package com.emclab.voucher.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.emclab.voucher.domain.FeedbackImage} entity.
 */
public class FeedbackImageDTO implements Serializable {

    private Long id;

    @NotNull
    private String content;

    private FeedbackDTO feedback;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FeedbackDTO getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackDTO feedback) {
        this.feedback = feedback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedbackImageDTO)) {
            return false;
        }

        FeedbackImageDTO feedbackImageDTO = (FeedbackImageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feedbackImageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedbackImageDTO{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", feedback=" + getFeedback() +
            "}";
    }
}
