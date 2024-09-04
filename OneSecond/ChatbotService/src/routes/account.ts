import express, { Request, Response, Router } from "express";
import { updateAccount } from "../services/accontService";
import Account from "../@types/account.interface";

const router: Router = express.Router();

//Account Controller

router.put("/:invitationCode", async (req, res) => {
  try {
    const invitationCode: string = req.params.invitationCode;
    const account: Account = req.body.account;

    const answer = await updateAccount(invitationCode, account);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Account Error",
      message: error,
    });
  }
});

export = router;
