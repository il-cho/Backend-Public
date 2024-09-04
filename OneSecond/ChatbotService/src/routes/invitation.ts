import express, { Request, Response, Router } from "express";
import {
  getInvitation,
  createInvitation,
  updateInvitation,
  deleteInvitation,
} from "../services/invitationService";
import Invitation from "../@types/invitation.interface";

const router: Router = express.Router();

//invitation

//TEST
//Prompt 조회
router.get("/:invitationCode", async (req, res) => {
  try {
    const invitationCode = req.params.invitationCode;

    const answer = await getInvitation(invitationCode);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Invitation View Error",
      message: error,
    });
  }
});

//Prompt 생성
router.post("/", async (req, res) => {
  try {
    const invitation: Invitation = req.body.invitation;

    const answer = await createInvitation(invitation);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Invitation Create Error",
      message: error,
    });
  }
});

//Prompt 수정
router.put("/", async (req, res) => {
  try {
    const invitation: Invitation = req.body.invitation;

    const answer = await updateInvitation(invitation);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Invitation Update Error",
      message: error,
    });
  }
});

//Prompt 정보 삭제
router.delete("/:invitationCode", async (req, res) => {
  try {
    const invitationCode = req.params.invitationCode;

    const answer = await deleteInvitation(invitationCode);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Invitation Delete Error",
      message: error,
    });
  }
});

export = router;
