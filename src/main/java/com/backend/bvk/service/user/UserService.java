package com.backend.bvk.service.user;

import com.backend.bvk.dto.request.UserRequest;
import com.backend.bvk.model.user.User;

public interface UserService {
    User findOrCreateUser(UserRequest payload);
}
