package com.codeit.side.user.application.service;

import com.codeit.side.common.adapter.exception.EmailAlreadyExistsException;
import com.codeit.side.common.adapter.exception.ErrorCode;
import com.codeit.side.common.application.port.out.CustomPasswordEncoder;
import com.codeit.side.common.application.port.out.FileUploadOutputPort;
import com.codeit.side.user.application.port.in.UserUseCase;
import com.codeit.side.user.application.port.out.UserCommandRepository;
import com.codeit.side.user.application.port.out.UserQueryRepository;
import com.codeit.side.user.domain.User;
import com.codeit.side.user.domain.command.UserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserUseCase {
    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final CustomPasswordEncoder encoder;
    private final FileUploadOutputPort fileUploader;

    @Override
    @Transactional
    public User create(UserCommand userCommand) {
        if (userQueryRepository.existsByEmail(userCommand.email())) {
            throw new EmailAlreadyExistsException(ErrorCode.INVALID_INPUT_VALUE, userCommand.email() + " 이미 가입된 이메일입니다.");
        }
        User user = userCommand.toDomain(encoder);

        return userCommandRepository.saveUser(user);
    }

    @Override
    public User getUser(String email) {
        return userQueryRepository.getByEmail(email)
                .toDomain();
    }

    @Override
    @Transactional
    public User updateUser(MultipartFile image, String email, String description) {
        fileUploader.validateImage(image);
        User user = userCommandRepository.updateUser(email, description, image != null);
        fileUploader.uploadImageToS3(image, "user/" + user.getId(), "image.jpg", "jpg");
        return user;
    }
}
