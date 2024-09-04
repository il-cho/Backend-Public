//MongoDB 연결
import mongoose, { ConnectOptions } from 'mongoose';

import dotenv from 'dotenv';
dotenv.config();

function connect() {
    
    const mongoURI = process.env.MONGODB_URI;

    if (typeof mongoURI === "undefined") {
        throw new Error("Env const `mongoURI` is not defined");
    }

    mongoose.connect(mongoURI, {
        useNewUrlParser: true,
        useUnifiedTopology: true,
      } as ConnectOptions)
      .then(() => {
        console.log('MongoDB connected successfully');
      })
      .catch(err => {
        console.error('MongoDB connection error:', err);
      });
}

export { connect };