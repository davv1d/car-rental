package com.davv1d.mapper.user;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.EmailUpdaterDto;
import org.springframework.stereotype.Component;

@Component
public class EmailUpdaterMapper {
    public EmailUpdater mapToEmailUpdater(final EmailUpdaterDto emailUpdaterDto) {
        return new EmailUpdater(emailUpdaterDto.getUsername(), emailUpdaterDto.getEmail());
    }
}
