import Chatbot from "../models/chatbot";
import Scehdule from "../@types/schedule.interface";

//프롬프트 데이터 수정
const updateSchedule = async (invitationCode: string, schedule: Scehdule) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };

    //존재하는 정보만 업데이트
    const update = {
      schedule: schedule,
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

export { updateSchedule };
