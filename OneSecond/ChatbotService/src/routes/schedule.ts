import express, { Request, Response, Router } from "express";
import Schedule from "../@types/schedule.interface";
import { updateSchedule } from "../services/scheduleService";

const router: Router = express.Router();

//schedule

router.put("/:invitationCode", async (req, res) => {
  try {
    const invitationCode = req.params.invitationCode;
    const schedule: Schedule = req.body.schedule;

    const answer = await updateSchedule(invitationCode, schedule);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Schedule Update Error",
      message: error,
    });
  }
});

export = router;
