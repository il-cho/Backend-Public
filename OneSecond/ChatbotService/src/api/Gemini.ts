import { GoogleGenerativeAI } from "@google/generative-ai";
import dotenv from "dotenv";
dotenv.config();

const VITE_GOOGLE_AI_STUDIO_API_KEY: string = process.env.VITE_GOOGLE_AI_STUDIO_API_KEY as string;
const genAI = new GoogleGenerativeAI(VITE_GOOGLE_AI_STUDIO_API_KEY);
const Gemini = genAI.getGenerativeModel({ model: "gemini-pro" });

export default Gemini;