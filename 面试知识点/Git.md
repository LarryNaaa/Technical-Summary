# Git

[TOC]

## 1. 常用的几个git命令

- 新增文件的命令：git add file或者git add .
- 提交文件的命令：git commit –m或者git commit –a
- 查看工作区状况：git status –s
- 拉取合并远程分支的操作：git fetch/git merge或者git pull
- 查看提交记录命令：git reflog

## 2. 提交时发生冲突，解释冲突是如何产生的吗？如何解决的？

- 开发过程中，我们都有自己的特性分支，所以冲突发生的并不多，但也碰到过。诸如公共类的公共方法，我和别人同时修改同一个文件，他提交后我再提交就会报冲突的错误。
- 发生冲突，在IDE里面一般都是对比本地文件和远程分支的文件，然后把远程分支上文件的内容手工修改到本地文件，然后再提交冲突的文件使其保证与远程分支的文件一致，这样才会消除冲突，然后再提交自己修改的部分。特别要注意下，修改本地冲突文件使其与远程仓库的文件保持一致后，需要提交后才能消除冲突，否则无法继续提交。必要时可与同事交流，消除冲突。
- 发生冲突，也可以使用命令。
  - 通过git stash命令，把工作区的修改提交到栈区，目的是保存工作区的修改；
  - 通过git pull命令，拉取远程分支上的代码并合并到本地分支，目的是消除冲突；
  - 通过git stash pop命令，把保存在栈区的修改部分合并到最新的工作空间中；

## 3. 如果本次提交误操作，如何撤销？

- 如果想撤销提交到索引区的文件，可以通过git reset HEAD file；
- 如果想撤销提交到本地仓库的文件，可以通过git reset –soft HEAD^n恢复当前分支的版本库至上一次提交的状态，索引区和工作空间不变更；
- 可以通过git reset –mixed HEAD^n恢复当前分支的版本库和索引区至上一次提交的状态，工作区不变更；
- 可以通过git reset –hard HEAD^n恢复当前分支的版本库、索引区和工作空间至上一次提交的状态。

## 4. 你使用过git stash命令吗？你一般什么情况下会使用它？

- 命令git stash是把工作区修改的内容存储在栈区。以下几种情况会使用到它：

  - 解决冲突文件时，会先执行git stash，然后解决冲突；
  - 遇到紧急开发任务但目前任务不能提交时，会先执行git stash，然后进行紧急任务的开发，然后通过git stash pop取出栈区的内容继续开发；
  - 切换分支时，当前工作空间内容不能提交时，会先执行git stash再进行分支切换；

## 5. 如何查看分支提交的历史记录？查看某个文件的历史记录呢？

- 查看分支的提交历史记录：

  - 命令git log –number：表示查看当前分支前number个详细的提交历史记录；
  - 命令git log –number –pretty=oneline：在上个命令的基础上进行简化，只显示sha-1码和提交信息；
  - 命令git reflog –number: 表示查看所有分支前number个简化的提交历史记录；
  - 命令git reflog –number –pretty=oneline：显示简化的信息历史信息；
  - 如果要查看某文件的提交历史记录，直接在上面命令后面加上文件名即可。注意：如果没有number则显示全部提交次数。

## 6. git fetch和git pull命令之间的区别？

- git fetch branch是把名为branch的远程分支拉取到本地；而git pull branch是在fetch的基础上，把branch分支与当前分支进行merge；因此pull = fetch + merge。

## 7. 使用过git merge和git rebase吗？它们之间有什么区别？

- git merge和git rebase都是合并分支的命令。
- git merge branch会把branch分支的差异内容pull到本地，然后与本地分支的内容一并形成一个committer对象提交到主分支上，合并后的分支与主分支一致；简单来说就合并两个分支并生成一个新的提交。
- git rebase branch会把branch分支优先合并到主分支，然后把本地分支的commit放到主分支后面，合并后的分支就好像从合并后主分支又拉了一个分支一样，本地分支本身不会保留提交历史。它会把整个feature分支移动到master分支的后面,有效地把所有master分支上新的提交并入过来。但是, rebase为原分支上每一个提交创建一个新的提交,重写了项目历史, 并且不会带来合并提交。

![](https://pic2.zhimg.com/80/v2-73db63a5abb3cac70f913155a854cf29_720w.jpg)

## 8. 能说一下git系统中HEAD、工作树和索引之间的区别吗？

- **HEAD文件**包含当前分支的引用（指针）；
- **工作树**是把当前分支检出到工作空间后形成的目录树，一般的开发工作都会基于工作树进行；
- **索引index文件**是对工作树进行代码修改后，通过add命令更新索引文件；GIT系统通过索引index文件生成tree对象；

## 9. 使用过git cherry-pick，有什么作用？

- 命令git cherry-pick可以把branch A的commit复制到branch B上。
- 在branch B上进行命令操作：
  - 复制单个提交：git cherry-pick commitId
  - 复制多个提交：git cherry-pick commitId1…commitId3
  - 注意：复制多个提交的命令不包含commitId1.

## 10. git跟其他版本控制器有啥区别？

- GIT是分布式版本控制系统，其他类似于SVN是集中式版本控制系统。
- 分布式区别于集中式在于：每个节点的地位都是平等，拥有自己的版本库，在没有网络的情况下，对工作空间内代码的修改可以提交到本地仓库，此时的本地仓库相当于集中式的远程仓库，可以基于本地仓库进行提交、撤销等常规操作，从而方便日常开发。

## 11. 我们在本地工程常会修改一些配置文件，这些文件不需要被提交，而我们又不想每次执行git status时都让这些文件显示出来，我们该如何操作？

- 首先利用命令touch .gitignore新建文件
- 然后往文件中添加需要忽略哪些文件夹下的什么类型的文件

```
$ vim .gitignore
$ cat .gitignore
/target/class
.settings
.imp
*.ini
```

- 注意：忽略/target/class文件夹下所有后缀名为.settings，.imp的文件，忽略所有后缀名为.ini的文件。

## 12. 如何把本地仓库的内容推向一个空的远程仓库？

- 首先确保本地仓库与远程之间是连同的。如果提交失败，则需要进行下面的命令进行连通：

  ```
  git remote add origin XXXX
  ```

  注意：XXXX是你的远程仓库地址。

- 如果是第一次推送，则进行下面命令：

  ```
  git push -u origin master
  ```
  
  注意：-u 是指定origin为默认主分支
  
- 之后的提交，只需要下面的命令：

  ```
  git push origin master
  ```
  
  

