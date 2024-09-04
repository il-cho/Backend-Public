package com.ssafy.invitationservice.account.adaptor.in.web;

import com.ssafy.invitationservice.account.adaptor.in.web.dto.CreateAccountDto;
import com.ssafy.invitationservice.account.adaptor.in.web.dto.UpdateAccountDto;
import com.ssafy.invitationservice.account.application.port.in.CreateAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.DeleteAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.GetAccountUseCase;
import com.ssafy.invitationservice.account.application.port.in.UpdateAccountUseCase;
import com.ssafy.invitationservice.account.mapper.AccountMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Invitation/Account", description = "정산 CRUD API")
@RequiredArgsConstructor
@RequestMapping("/invitation/account")
@Slf4j
public class AccountController {
    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountUseCase getAccountUseCase;
    private final UpdateAccountUseCase updateAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;
    private final AccountMapper mapper;

    @PostMapping("")
    @Operation(summary = "초대장 정산 정보 추가 API",
            description = "초대장 코드, 금액, 은행, 계좌번호를 입력하여 계좌 정보를 추가한다. (access token)")
    public ResponseEntity<?> createBankInfo( @Validated @RequestBody CreateAccountDto createAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createAccountUseCase.saveAccount(mapper.toAccount(createAccountDto)));
    }

    @GetMapping("/{invitationCode}")
    @Operation(summary = "초대장 정산 정보 조회 API", description = "초대장 코드에 해당하는 초대장의 금액, 은행, 계좌번호를 조회한다. (access token)")
    public ResponseEntity<?> viewBankInfo(@PathVariable("invitationCode") String invitationCode) {
        return ResponseEntity.ok(
                mapper.toCreateAccountDto(getAccountUseCase.findAccountByInvitationCode(invitationCode))
        );
    }

    @PutMapping("")
    @Operation(summary = "초대장정산 정보 수정 API", description = "초대장 코드에 해당하는 초대장의 금액, 은행, 계좌번호를 수정한다. (access token)")
    public ResponseEntity<?> updateBankInfo(@Validated @RequestBody UpdateAccountDto updateAccountDto) {
        return ResponseEntity.ok(
                mapper.toUpdateAccountDto(updateAccountUseCase.updateAccount(mapper.toAccount(updateAccountDto)))
        );
    }


    @DeleteMapping("/{invitationCode}")
    @Operation(summary = "초대장 정산 정보 삭제 API", description = "초대장 코드에 해당하는 초대장의 금액, 은행, 계좌번호를 삭제한다. (access token)")
    public ResponseEntity<?> deleteBankInfo(@PathVariable String invitationCode) {
        return ResponseEntity.ok(deleteAccountUseCase.deleteAccountByInvitationCode(invitationCode));
    }
}
