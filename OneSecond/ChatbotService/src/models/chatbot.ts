import { Schema, model } from "mongoose";

import { Prompt, PromptModel } from "../@types/prompt.interface";

import Invitation from "../@types/invitation.interface";
import Scehdule from "../@types/schedule.interface";
import Place from "../@types/place.interface";
import Account from "../@types/account.interface";

const InvitationSchema = new Schema<Invitation>({
  invitationCode: { type: String },
  inviter: { type: String },
  title: { type: String },
  description: { type: String },
  confirmedDate: { type: String },
  attendees: {
    user_id: [{ type: Number }],
  },
});

const ScheduleSchema = new Schema<Scehdule>({
  startDate: { type: String },
  endDate: { type: String },
  possibleDate: [
    {
      userId: { type: Number },
      dateList: [{ type: String }],
    },
  ],
  isConfirmed: { type: Boolean },
});

const PlaceSchema = new Schema<Place>({
  placeName: { type: String },
  placeAddress: { type: String },
});

const AccountSchema = new Schema<Account>({
  price: { type: Number },
  bank: { type: String },
  bankAccount: { type: String },
  name: { type: String },
});

const promptSchema = new Schema<Prompt, PromptModel>({
  invitation: { type: InvitationSchema },
  schedule: ScheduleSchema,
  place: [PlaceSchema],
  account: AccountSchema,
});

const Chatbot = model("chatbot", promptSchema);

export default Chatbot;
