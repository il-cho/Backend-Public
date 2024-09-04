import Gemini from "../api/Gemini";
import Chatbot from "../models/chatbot";

async function run(invitationCode: string, prompt: string) {
  try {
    const fillter = {
      "invitation.invitationCode": invitationCode,
    };

    const settingInfo = await Chatbot.findOne(fillter);

    let question = "모임 정보는 다음과 같아. ";
    question += JSON.stringify(settingInfo);

    question += "줄바꿈은 \n으로 표시해줘.";
    question += " 모임 정보를 바탕으로 다음 질문에 자연스러운 완성된 문장으로 답변해줘.";
    question += prompt;

    const result = await Gemini.generateContent(question);
    const response = result.response;
    const text = response.text();

    return text;
  } catch (error: any) {
    console.error(error);
    throw new Error("Gemini Internal Error");
  }
}

const chatbot = async (invitationCode: string, prompt: string) => {
  try {
    const answer = await run(invitationCode, prompt);

    return answer;
  } catch (error) {
    console.error(error);
    throw new Error("Gemini Internal Error");
  }
};

export default chatbot;
