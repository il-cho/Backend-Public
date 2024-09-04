import Chatbot from "../models/chatbot";
import Place from "../@types/place.interface";

//프롬프트 데이터 수정
const updatePlace = async (invitationCode: string, place: Place) => {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };

    //존재하는 정보만 업데이트
    const update = {
      place: place,
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

export { updatePlace };
