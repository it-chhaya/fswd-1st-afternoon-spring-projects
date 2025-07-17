package kh.edu.cstad.mbapi.mapper;

import kh.edu.cstad.mbapi.domain.Account;
import kh.edu.cstad.mbapi.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "accountType.type", target = "accountType")
    AccountResponse toAccountResponse(Account account);

}
