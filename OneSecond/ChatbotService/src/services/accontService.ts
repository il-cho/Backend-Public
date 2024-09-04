import Chatbot from "../models/chatbot";
import Account from "../@types/account.interface";

//프롬프트 데이터 수정
const updateAccount = async (invitationCode: string, account: Account) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };

    //존재하는 정보만 업데이트
    const update = {
      account: account,
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

export { updateAccount };
