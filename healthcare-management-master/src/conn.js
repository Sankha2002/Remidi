const mongoose = require('mongoose')

// mongoose.Promise=global.Promise

mongoose.connect('mongodb://localhost:27017/HealthcareManagement',{
     useNewUrlParser : true, 
     useUnifiedTopology : true , 
     useCreateIndex : true,
     useFindAndModify : false
}).then(()=>{
     console.log("connection succesfull...")
}).catch((err)=>{
    console.log(err)
})