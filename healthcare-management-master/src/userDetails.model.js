const mongoose = require('mongoose')

const userDetails = new mongoose.Schema({
    name: {
        type: String,
        required: true,
    },
    email: {
        type: String,
        required: true,
    },
    phone_number: {
        type: String,
        required: true,
    },
    password: {
        type: String,
        required: true,
    },
}, {
    timestamps: true
})

//COLLECTION

const UserDetails = new mongoose.model('user_details', userDetails)

module.exports = UserDetails
