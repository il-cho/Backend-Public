import { Model } from "mongoose";
import Invitation from "./invitation.interface";
import Account from "./account.interface";
import Place from "./place.interface";
import Scehdule from "./schedule.interface";

export interface Prompt {
  invitation: Invitation;
  schedule: Scehdule | null;
  place: Place | null;
  account: Account | null;
}

export interface PromptModel extends Model<Prompt> {}
