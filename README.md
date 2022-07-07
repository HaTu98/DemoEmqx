# DemoEmqx

#1 Install the required dependency

	sudo apt update && sudo apt install -y \
	apt-transport-https \
	ca-certificates \
	curl \
	gnupg-agent \
	software-properties-common

#2 Add the GPG key for EMQ X
	
	curl -fsSL https://repos.emqx.io/gpg.pub | sudo apt-key add -

	sudo apt-key fingerprint 3E640D53
	
#3 
	
	sudo add-apt-repository \
	"deb [arch=amd64] https://repos.emqx.io/emqx-ce/deb/ubuntu/ \
	$(lsb_release -cs) \
	stable"
	
#4 install
	sudo apt update
	sudo apt install emqx

#5 start 
	emqx start
	emqx_ctl status
	# sudo systemctl start emqx
	# sudo service emqx start
