const mongoose = require("mongoose");


const DataSchema = new mongoose.Schema({}, { strict: false });
const DataModel = mongoose.model("Data", DataSchema);

module.exports = DataModel;