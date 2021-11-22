package com.music.service.servImpl;

import com.music.domain.Admin;

public interface AdminService {
    boolean verifyPassword(String name, String password);
    boolean updateAdminService(Admin admin);
}
