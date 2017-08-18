-- 首先创建补丁表
use QSTMGood
go

if object_id('ttm_patch', 'U') is null
	begin
		-- 创建 ttm 的补丁表
		select * into ttm_patch from tTM01 where 1 = 2;
		-- 增加 3 列，分别用于标识是哪一个分类的（1-45）、是新数据(1)、是更新(1)
		alter table ttm_patch add brand_type int; 
		alter table ttm_patch add is_new int; 
		alter table ttm_patch add is_update int; 
	end
go

if object_id('ttm_delete', 'U') is null
	begin
		-- 创建 ttm 的删除表
		select * into ttm_delete from tTM01 where 1 = 2;
		-- 增加 3 列，分别用于标识是哪一个分类的（1-45）、是新数据(1)、是更新(1)
		alter table ttm_delete add brand_type int; 
		alter table ttm_delete add is_new int; 
		alter table ttm_delete add is_update int; 
	end
go

-- 创建触发器 tgr_ttm01_insert
if object_id('tgr_ttm01_insert', 'tr') is not null
	drop trigger tgr_ttm01_insert;
go

create trigger tgr_ttm01_insert
on ttm01
	for insert
as
	-- 定义变量, 由于触发器中变量不能是 text、ntext 或 image 数据类型，所以对于这些字段，下面再执行 update 脚本
	declare @fGGQ int, @fBGQ int, @fTMCHIN nvarchar(100), @fTMENG nvarchar(100), @fTMPY nvarchar(100), @fTMHEAD nvarchar(20), @fTMSZ nvarchar(20), @fSQDATE nvarchar(8), @fZCDATE nvarchar(8), @fSQR1 nvarchar(160), @fBGQH1 int, @fBGDATE1 nvarchar(8), @fBGBZ1 tinyint, @fSQR2 nvarchar(160), @fBGQH2 int, @fBGDATE2 nvarchar(8), @fBGBZ2 tinyint, @fSQR3 nvarchar(160), @fBGQH3 int, @fBGDATE3 nvarchar(8), @fBGBZ3 tinyint, @fAddr nvarchar(160), @fBGQH4 int, @fBGDATE4 nvarchar(8), @fDLZZ nvarchar(60), @fTMTYPEID tinyint, @fUserID tinyint, @fBGUserID tinyint, @fXGUserID int, @fTMJPM nvarchar(50), @fArea nvarchar(10), @fBGYM1 int, @fBGYM2 int, @fBGYM3 int, @fBGYM4 int, @fCSYM int, @fZCYM int, @fSPZB nvarchar(300), @fJZDATE nvarchar(10), @fCSDATE nvarchar(10), @fZCQ int, @fSBLX nvarchar(10), @fZDYS nvarchar(2), @fSFGY nvarchar(2), @fTMSZALB nvarchar(20), @fsqr1zt int, @ftmid varbinary(64);

	-- 定义游标
	declare cur_ttm cursor for select fGGQ, fBGQ, fTMCHIN, fTMENG, fTMPY, fTMHEAD, fTMSZ, fSQDATE, fZCDATE, fSQR1, fBGQH1, fBGDATE1, fBGBZ1, fSQR2, fBGQH2, fBGDATE2, fBGBZ2, fSQR3, fBGQH3, fBGDATE3, fBGBZ3, fAddr, fBGQH4, fBGDATE4, fDLZZ, fTMTYPEID, fUserID, fBGUserID, fXGUserID, fTMJPM, fArea, fBGYM1, fBGYM2, fBGYM3, fBGYM4, fCSYM, fZCYM, fSPZB, fJZDATE, fCSDATE, fZCQ, fSBLX, fZDYS, fSFGY, fTMSZALB, fsqr1zt, ftmid from inserted;

	open cur_ttm;

	fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;

	while @@fetch_status = 0
		begin
			if (exists (select * from ttm_patch where ftmid = @ftmid and brand_type = 1))
				begin
					update ttm_patch set fGGQ = @fGGQ, fBGQ = @fBGQ, fTMCHIN = @fTMCHIN, fTMENG = @fTMENG, fTMPY = @fTMPY, fTMHEAD = @fTMHEAD, fTMSZ = @fTMSZ, fSQDATE = @fSQDATE, fZCDATE = @fZCDATE, fSQR1 = @fSQR1, fBGQH1 = @fBGQH1, fBGDATE1 = @fBGDATE1, fBGBZ1 = @fBGBZ1, fSQR2 = @fSQR2, fBGQH2 = @fBGQH2, fBGDATE2 = @fBGDATE2, fBGBZ2 = @fBGBZ2, fSQR3 = @fSQR3, fBGQH3 = @fBGQH3, fBGDATE3 = @fBGDATE3, fBGBZ3 = @fBGBZ3, fAddr = @fAddr, fBGQH4 = @fBGQH4, fBGDATE4 = @fBGDATE4, fDLZZ = @fDLZZ, fTMTYPEID = @fTMTYPEID, fUserID = @fUserID, fBGUserID = @fBGUserID, fXGUserID = @fXGUserID, fTMJPM = @fTMJPM, fArea = @fArea, fBGYM1 = @fBGYM1, fBGYM2 = @fBGYM2, fBGYM3 = @fBGYM3, fBGYM4 = @fBGYM4, fCSYM = @fCSYM, fZCYM = @fZCYM, fSPZB = @fSPZB, fJZDATE = @fJZDATE, fCSDATE = @fCSDATE, fZCQ = @fZCQ, fSBLX = @fSBLX, fZDYS = @fZDYS, fSFGY = @fSFGY, fTMSZALB = @fTMSZALB, fsqr1zt = @fsqr1zt, ftmid = @ftmid, is_update = 1 where ftmid = @ftmid and brand_type = 1;

					print 'ttm01 insert, ttm_patch update successful';
				end
			else
				begin
					insert into ttm_patch values(@fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, null, null, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid, 1, 1, 0);

					print 'ttm01 insert, ttm_patch insert successful';
				end

			update a set a.fBZ = b.fBZ, a.fSYSP = b.fSYSP from ttm_patch a, tTM01 b where a.ftmid = b.ftmid and a.brand_type = 1;

			fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;
		end

	close cur_ttm;
	deallocate cur_ttm;
go


-- 创建触发器 tgr_ttm01_update
if object_id('tgr_ttm01_update', 'tr') is not null
	drop trigger tgr_ttm01_update;
go

create trigger tgr_ttm01_update
on ttm01
	for update
as
	-- 定义变量, 由于触发器中变量不能是 text、ntext 或 image 数据类型，所以对于这些字段，下面再执行 update 脚本
	declare @fGGQ int, @fBGQ int, @fTMCHIN nvarchar(100), @fTMENG nvarchar(100), @fTMPY nvarchar(100), @fTMHEAD nvarchar(20), @fTMSZ nvarchar(20), @fSQDATE nvarchar(8), @fZCDATE nvarchar(8), @fSQR1 nvarchar(160), @fBGQH1 int, @fBGDATE1 nvarchar(8), @fBGBZ1 tinyint, @fSQR2 nvarchar(160), @fBGQH2 int, @fBGDATE2 nvarchar(8), @fBGBZ2 tinyint, @fSQR3 nvarchar(160), @fBGQH3 int, @fBGDATE3 nvarchar(8), @fBGBZ3 tinyint, @fAddr nvarchar(160), @fBGQH4 int, @fBGDATE4 nvarchar(8), @fDLZZ nvarchar(60), @fTMTYPEID tinyint, @fUserID tinyint, @fBGUserID tinyint, @fXGUserID int, @fTMJPM nvarchar(50), @fArea nvarchar(10), @fBGYM1 int, @fBGYM2 int, @fBGYM3 int, @fBGYM4 int, @fCSYM int, @fZCYM int, @fSPZB nvarchar(300), @fJZDATE nvarchar(10), @fCSDATE nvarchar(10), @fZCQ int, @fSBLX nvarchar(10), @fZDYS nvarchar(2), @fSFGY nvarchar(2), @fTMSZALB nvarchar(20), @fsqr1zt int, @ftmid varbinary(64);

	-- 定义游标
	declare cur_ttm cursor for select fGGQ, fBGQ, fTMCHIN, fTMENG, fTMPY, fTMHEAD, fTMSZ, fSQDATE, fZCDATE, fSQR1, fBGQH1, fBGDATE1, fBGBZ1, fSQR2, fBGQH2, fBGDATE2, fBGBZ2, fSQR3, fBGQH3, fBGDATE3, fBGBZ3, fAddr, fBGQH4, fBGDATE4, fDLZZ, fTMTYPEID, fUserID, fBGUserID, fXGUserID, fTMJPM, fArea, fBGYM1, fBGYM2, fBGYM3, fBGYM4, fCSYM, fZCYM, fSPZB, fJZDATE, fCSDATE, fZCQ, fSBLX, fZDYS, fSFGY, fTMSZALB, fsqr1zt, ftmid from inserted;

	open cur_ttm;

	fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;

	while @@fetch_status = 0
		begin
			if (exists (select * from ttm_patch where ftmid = @ftmid and brand_type = 1))
				begin
					update ttm_patch set fGGQ = @fGGQ, fBGQ = @fBGQ, fTMCHIN = @fTMCHIN, fTMENG = @fTMENG, fTMPY = @fTMPY, fTMHEAD = @fTMHEAD, fTMSZ = @fTMSZ, fSQDATE = @fSQDATE, fZCDATE = @fZCDATE, fSQR1 = @fSQR1, fBGQH1 = @fBGQH1, fBGDATE1 = @fBGDATE1, fBGBZ1 = @fBGBZ1, fSQR2 = @fSQR2, fBGQH2 = @fBGQH2, fBGDATE2 = @fBGDATE2, fBGBZ2 = @fBGBZ2, fSQR3 = @fSQR3, fBGQH3 = @fBGQH3, fBGDATE3 = @fBGDATE3, fBGBZ3 = @fBGBZ3, fAddr = @fAddr, fBGQH4 = @fBGQH4, fBGDATE4 = @fBGDATE4, fDLZZ = @fDLZZ, fTMTYPEID = @fTMTYPEID, fUserID = @fUserID, fBGUserID = @fBGUserID, fXGUserID = @fXGUserID, fTMJPM = @fTMJPM, fArea = @fArea, fBGYM1 = @fBGYM1, fBGYM2 = @fBGYM2, fBGYM3 = @fBGYM3, fBGYM4 = @fBGYM4, fCSYM = @fCSYM, fZCYM = @fZCYM, fSPZB = @fSPZB, fJZDATE = @fJZDATE, fCSDATE = @fCSDATE, fZCQ = @fZCQ, fSBLX = @fSBLX, fZDYS = @fZDYS, fSFGY = @fSFGY, fTMSZALB = @fTMSZALB, fsqr1zt = @fsqr1zt, ftmid = @ftmid, is_update = 1 where ftmid = @ftmid and brand_type = 1;

					print 'ttm01 update, ttm_patch update successful';
				end
			else
				begin
					insert into ttm_patch values(@fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, null, null, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid, 1, 0, 1);

					print 'ttm01 update, ttm_patch insert successful';
				end

			update a set a.fBZ = b.fBZ, a.fSYSP = b.fSYSP from ttm_patch a, tTM01 b where a.ftmid = b.ftmid and a.brand_type = 1;

			fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;
		end

	close cur_ttm;
	deallocate cur_ttm;
go


-- 创建触发器 tgr_ttm01_delete
if object_id('tgr_ttm01_delete', 'tr') is not null
	drop trigger tgr_ttm01_delete;
go

create trigger tgr_ttm01_delete
on ttm01
	for delete
as
	-- 定义变量
	declare @fGGQ int, @fBGQ int, @fTMCHIN nvarchar(100), @fTMENG nvarchar(100), @fTMPY nvarchar(100), @fTMHEAD nvarchar(20), @fTMSZ nvarchar(20), @fSQDATE nvarchar(8), @fZCDATE nvarchar(8), @fSQR1 nvarchar(160), @fBGQH1 int, @fBGDATE1 nvarchar(8), @fBGBZ1 tinyint, @fSQR2 nvarchar(160), @fBGQH2 int, @fBGDATE2 nvarchar(8), @fBGBZ2 tinyint, @fSQR3 nvarchar(160), @fBGQH3 int, @fBGDATE3 nvarchar(8), @fBGBZ3 tinyint, @fAddr nvarchar(160), @fBGQH4 int, @fBGDATE4 nvarchar(8), @fDLZZ nvarchar(60), @fTMTYPEID tinyint, @fUserID tinyint, @fBGUserID tinyint, @fXGUserID int, @fTMJPM nvarchar(50), @fArea nvarchar(10), @fBGYM1 int, @fBGYM2 int, @fBGYM3 int, @fBGYM4 int, @fCSYM int, @fZCYM int, @fSPZB nvarchar(300), @fJZDATE nvarchar(10), @fCSDATE nvarchar(10), @fZCQ int, @fSBLX nvarchar(10), @fZDYS nvarchar(2), @fSFGY nvarchar(2), @fTMSZALB nvarchar(20), @fsqr1zt int, @ftmid varbinary(64);

	-- 定义游标
	declare cur_ttm cursor for select fGGQ, fBGQ, fTMCHIN, fTMENG, fTMPY, fTMHEAD, fTMSZ, fSQDATE, fZCDATE, fSQR1, fBGQH1, fBGDATE1, fBGBZ1, fSQR2, fBGQH2, fBGDATE2, fBGBZ2, fSQR3, fBGQH3, fBGDATE3, fBGBZ3, fAddr, fBGQH4, fBGDATE4, fDLZZ, fTMTYPEID, fUserID, fBGUserID, fXGUserID, fTMJPM, fArea, fBGYM1, fBGYM2, fBGYM3, fBGYM4, fCSYM, fZCYM, fSPZB, fJZDATE, fCSDATE, fZCQ, fSBLX, fZDYS, fSFGY, fTMSZALB, fsqr1zt, ftmid from deleted;

	open cur_ttm;

	fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;

	while @@fetch_status = 0
		begin
			if (exists (select * from ttm_delete where ftmid = @ftmid and brand_type = 1))
				begin
					update ttm_delete set fGGQ = @fGGQ, fBGQ = @fBGQ, fTMCHIN = @fTMCHIN, fTMENG = @fTMENG, fTMPY = @fTMPY, fTMHEAD = @fTMHEAD, fTMSZ = @fTMSZ, fSQDATE = @fSQDATE, fZCDATE = @fZCDATE, fSQR1 = @fSQR1, fBGQH1 = @fBGQH1, fBGDATE1 = @fBGDATE1, fBGBZ1 = @fBGBZ1, fSQR2 = @fSQR2, fBGQH2 = @fBGQH2, fBGDATE2 = @fBGDATE2, fBGBZ2 = @fBGBZ2, fSQR3 = @fSQR3, fBGQH3 = @fBGQH3, fBGDATE3 = @fBGDATE3, fBGBZ3 = @fBGBZ3, fAddr = @fAddr, fBGQH4 = @fBGQH4, fBGDATE4 = @fBGDATE4, fDLZZ = @fDLZZ, fTMTYPEID = @fTMTYPEID, fUserID = @fUserID, fBGUserID = @fBGUserID, fXGUserID = @fXGUserID, fTMJPM = @fTMJPM, fArea = @fArea, fBGYM1 = @fBGYM1, fBGYM2 = @fBGYM2, fBGYM3 = @fBGYM3, fBGYM4 = @fBGYM4, fCSYM = @fCSYM, fZCYM = @fZCYM, fSPZB = @fSPZB, fJZDATE = @fJZDATE, fCSDATE = @fCSDATE, fZCQ = @fZCQ, fSBLX = @fSBLX, fZDYS = @fZDYS, fSFGY = @fSFGY, fTMSZALB = @fTMSZALB, fsqr1zt = @fsqr1zt, ftmid = @ftmid, is_update = 1 where ftmid = @ftmid and brand_type = 1;

					print 'ttm01 delete, ttm_delete update successful';
				end
			else
				begin
					insert into ttm_delete values(@fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, null, null, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid, 1, 1, 0);

					print 'ttm01 delete, ttm_delete insert successful';
				end

			fetch next from cur_ttm into @fGGQ, @fBGQ, @fTMCHIN, @fTMENG, @fTMPY, @fTMHEAD, @fTMSZ, @fSQDATE, @fZCDATE, @fSQR1, @fBGQH1, @fBGDATE1, @fBGBZ1, @fSQR2, @fBGQH2, @fBGDATE2, @fBGBZ2, @fSQR3, @fBGQH3, @fBGDATE3, @fBGBZ3, @fAddr, @fBGQH4, @fBGDATE4, @fDLZZ, @fTMTYPEID, @fUserID, @fBGUserID, @fXGUserID, @fTMJPM, @fArea, @fBGYM1, @fBGYM2, @fBGYM3, @fBGYM4, @fCSYM, @fZCYM, @fSPZB, @fJZDATE, @fCSDATE, @fZCQ, @fSBLX, @fZDYS, @fSFGY, @fTMSZALB, @fsqr1zt, @ftmid;
		end

	close cur_ttm;
	deallocate cur_ttm;
go


