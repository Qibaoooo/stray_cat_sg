provider "aws" {
  region     = "ap-southeast-1"
  access_key = "AKIA2DANB6IEA7RGADXD"
  secret_key = "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"
}

# VPC
resource "aws_vpc" "test-env" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support   = true
}

# SUBNET
resource "aws_subnet" "subnet-uno" {
  cidr_block        = cidrsubnet(aws_vpc.test-env.cidr_block, 3, 1)
  vpc_id            = aws_vpc.test-env.id
  availability_zone = "ap-southeast-1a"
}

# ELASTIC IP
resource "aws_eip" "ip-test-env" {
  instance = aws_instance.myec2.id
}

# ROUTE TABLE
resource "aws_route_table" "route-table-test-env" {
  vpc_id = aws_vpc.test-env.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.test-env-gw.id
  }
}
resource "aws_route_table_association" "subnet-association" {
  subnet_id      = aws_subnet.subnet-uno.id
  route_table_id = aws_route_table.route-table-test-env.id
}

# INTERNET GATEWAY
# SECURITY GROUP

# EC2
resource "aws_instance" "myec2" {
  ami             = "ami-0e4b5d31e60aa0acd"
  instance_type   = "t2.micro"
  key_name        = "jms"
  vpc_security_group_ids = ["${aws_security_group.ingress-all-test.id}"]
  subnet_id = "${aws_subnet.subnet-uno.id}"

  tags = {
    Name = "ec2-jms"
  }
}
