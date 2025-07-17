package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.AccountResponse;
import kh.edu.cstad.mbapi.dto.CreateAccountRequest;

public interface AccountService {

    AccountResponse createNew(CreateAccountRequest createAccountRequest);

}
