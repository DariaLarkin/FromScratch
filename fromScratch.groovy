node {

  properties([
      // below line sets "Diskard builds more than 5"
      buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5')), 
      [$class: 'RebuildSettings', autoRebuild: false, rebuildDisabled: false], 
      
      // below line triggers this job every minute
      pipelineTriggers([pollSCM('* * * * *')])]) // that is mean, read the github and if see some changes - perform the task, if not do not do nothing
  

   stage("Pull Repo"){
       git "https://github.com/DariaLarkin/FromScratch.git"
   }
  stage("Install Prerequisites"){
      // in Groovy language """ in the begining and """ in the end mean i wanna put multiple commands
       sh """   
       ssh centos@jenkins_worker1.acirrustech.com          sudo yum install httpd -y
      
       """
   }
   stage("Copy artifacts"){
    shh """
       scp -r centos@jenkins_worker1.acirrustech.com:/tmp
       ssh centos@jenkins_worker1.acirrustech.com              sudo cp -r /tmp/index.html /var/www/html
       ssh centos@jenkins_worker1.acirrustech.com              sudo cp /tmp/style.css /var/www/html
       ssh centos@jenkins_worker1.acirrustech.com              sudo chown centos:centos /var/www/html
       ssh centos@jenkins_worker1.acirrustech.com              sudo chmos 777 /var/www/html
       """
   }
   stage("Restart web server"){
       sh "ssh centos@jenkins_worker1.acirrustech.com              sudo systemctl restart httpd"
   }
   stage("Slack"){
       slacksend color: '#BADA55', message: 'Hello, World'
   }
}