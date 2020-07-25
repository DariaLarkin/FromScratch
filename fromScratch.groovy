node {

  properties([
      // below line sets "Diskard builds more than 5"
      buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
      [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false], 
      
      // below line triggers this job every minute
      pipelineTriggers([cron('* * * * *')
    ])
  ])





   stage("Stage1"){
       echo "hello"
   }
stage("Stage1"){
       echo "hello"
   }
   stage("Stage2"){
       echo "hello"
   }
   stage("Stage3"){
       echo "hello"
   }
   stage("Stage4"){
       echo "hello"
   }
}