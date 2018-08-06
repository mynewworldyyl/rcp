select namespace0_.id as id1_13_, namespace0_.created_on as created_2_13_, 
namespace0_.descr as descr3_13_, namespace0_.namespace as namespac4_13_, 
namespace0_.updated_on as updated_5_13_ 
from t_namespace namespace0_ 
inner join t_attr attrs1_ on namespace0_.id=attrs1_.namespace 
where namespace0_.namespace like '%' or attrs1_.name like '%';

select distinct namespace0_.id as id1_13_, namespace0_.created_on as created_2_13_, namespace0_.descr as descr3_13_, 
namespace0_.namespace as namespac4_13_, namespace0_.updated_on as updated_5_13_ 
from t_namespace namespace0_ 
left outer join t_attr attrs1_ on namespace0_.id=attrs1_.namespace 
where namespace0_.namespace like '%t%' or attrs1_.name like '%t%'
