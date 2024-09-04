import express, { Request, Response } from "express";
import { Eureka } from "eureka-js-client";
import bodyParser from "body-parser";
import os from "os";

import * as db from "./utils/db";

//Router Import
import chatbotRouter from "./routes/chatbot";
import invitationRouter from "./routes/invitation";
import accountRouter from "./routes/account";
import scheduleRouter from "./routes/schedule";
import placeRouter from "./routes/place";

const app = express();

db.connect(); // Connect to MongoDB

const port: number = 8083;

app.use(express.urlencoded({ extended: true }));
app.use(bodyParser.json());

//Use Router
app.use("/chatbot/prompt", chatbotRouter);
app.use("/chatbot/invitation/account", accountRouter);
app.use("/chatbot/invitation", invitationRouter);
app.use("/chatbot/schedule", scheduleRouter);
app.use("/chatbot/place", placeRouter);

const instanceId = `${os.hostname()}:${port}`;

// Eureka Client 설정 및 시작
const client = new Eureka({
  instance: {
    instanceId: instanceId,
    app: "chatbot-service",
    hostName: "il-cho.site",
    ipAddr: "discovery",
    statusPageUrl: `https://${instanceId}:${port}/status`,
    port: {
      $: port,
      "@enabled": true,
    },
    vipAddress: "chatbot-service",
    dataCenterInfo: {
      "@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
      name: "MyOwn",
    },
  },
  eureka: {
    host: "il-cho.site",
    port: 8761,
    servicePath: "/eureka/apps/",
  },
});

app.listen(port, () => {
  console.log(`server is listening at localhost:${port}`);

  client.start((error: Error | null) => {
    if (error) {
      console.error("Error starting Eureka client:", error);
    }
    console.log(error || "Eureka client started successfully");
  });
});
