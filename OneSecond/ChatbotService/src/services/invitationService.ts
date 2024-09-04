import { Prompt } from "../@types/prompt.interface";
import Chatbot from "../models/chatbot";
import Invitation from "../@types/invitation.interface";

//TEST API
//프롬프트 데이터 조회
const getInvitation = async (invitationCode: string) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };
    const prompt = await Chatbot.findOne(fillter);

    return prompt;
  } catch (error) {
    console.error(error);
    throw new Error("MongoDB Error");
  }
};

//프롬프트 데이터 생성
const createInvitation = async (invitation: Invitation) => {
  try {
    //초대장 초기 값 추가
    const prompt: Prompt = {
      invitation: invitation,
      account: null,
      schedule: null,
      place: null,
    };

    const promptInfo = new Chatbot(prompt);
    await promptInfo.save();

    return promptInfo;
  } catch (error) {
    console.error(error);
    throw new Error("MongoDB Error");
  }
};

//프롬프트 데이터 수정
const updateInvitation = async (invitation: Invitation) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitation.invitationCode,
    };

    //존재하는 정보만 업데이트
    const update = {
      invitation: invitation,
    };

    const promptInfo = await Chatbot.findOneAndUpdate(fillter, update, {
      new: true,
      runValidators: true,
    });

    return promptInfo;
  } catch (error) {
    console.error(error);
    throw new Error("MongoDB Error");
  }
};

//프롬프트 데이터 삭제
const deleteInvitation = async (invitationCode: string) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };
    const prompt = await Chatbot.findOneAndDelete(fillter);

    return prompt;
  } catch (error) {
    console.error(error);
    throw new Error("MongoDB Error");
  }
};

export { getInvitation, createInvitation, updateInvitation, deleteInvitation };
