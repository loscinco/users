package org.agendifive.users.service;

import org.agendifive.users.model.Response;

public interface AuthInterface {
    Response authenticate(String username, String password);
}
