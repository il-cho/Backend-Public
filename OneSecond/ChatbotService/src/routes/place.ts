import express, { Request, Response, Router } from "express";
import Place from "../@types/place.interface";
import { updatePlace } from "../services/placeService";

const router: Router = express.Router();

//place
router.put("/:invitationCode", async (req, res) => {
  try {
    const invitationCode: string = req.params.invitationCode;
    const place: Place = req.body.place;

    const answer = await updatePlace(invitationCode, place);

    return res.status(200).json({
      answer,
    });
  } catch (error: any) {
    console.error(error);
    return res.status(500).json({
      error: "Place Update Error",
      message: error,
    });
  }
});

export = router;
