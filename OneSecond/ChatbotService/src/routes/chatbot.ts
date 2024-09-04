import express, { Request, Response, Router } from "express";
import chatbot from "../services/chatbotService";

const router: Router = express.Router();

router.post("/:invitationCode", async (req, res) => {
  try {
    const invitationCode: string = req.params.invitationCode;
    const prompt: string = req.body.prompt;

    const answer = await chatbot(invitationCode, prompt);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Chatbot Question Error",
      message: error,
    });
  }
});

export = router;
