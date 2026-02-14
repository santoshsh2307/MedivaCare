package com.hms.profile_service.serviceImpl;

import com.hms.profile_service.dto.BranchRequest;
import com.hms.profile_service.dto.BranchResponse;
import com.hms.profile_service.dto.BranchTreeBuilder;
import com.hms.profile_service.dto.BranchTreeResponse;
import com.hms.profile_service.model.Branch;
import com.hms.profile_service.model.User;
import com.hms.profile_service.model.UserBranch;
import com.hms.profile_service.repository.BranchRepository;
import com.hms.profile_service.repository.UserBranchRepository;
import com.hms.profile_service.repository.UserRepository;
import com.hms.profile_service.service.BranchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    private final UserRepository userRepository;

    private final UserBranchRepository userBranchRepository;

    public BranchServiceImpl(BranchRepository branchRepository, UserRepository userRepository, UserBranchRepository userBranchRepository) {
        this.branchRepository = branchRepository;
        this.userRepository=userRepository;
        this.userBranchRepository = userBranchRepository;
    }


    @Transactional
    public BranchResponse create(BranchRequest request, Long creatorId) {

        Branch branch = new Branch();
        branch.setBranchName(request.getBranchName());
        branch.setLocation(request.getLocation());
        branch.setStatus("ACTIVE");

        if(request.getParentId() != null){
            Branch parent = branchRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent branch not found"));
            branch.setParent(parent);
        }

        Branch saved = branchRepository.save(branch);

        // Map creator (super admin or admin) to branch
        User creator = userRepository.findById(creatorId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserBranch userBranch = new UserBranch();
        userBranch.setUser(creator);
        userBranch.setBranch(saved);
        userBranch.setAccessLevel("MANAGE");

        userBranchRepository.save(userBranch);

        return mapToDto(saved);
    }


    private BranchResponse mapToDto(Branch branch) {
        return BranchResponse.builder()
                .id(branch.getId())
                .branchName(branch.getBranchName())
                .location(branch.getLocation())
                .status(branch.getStatus())
                .parentId(branch.getParent() != null ? branch.getParent().getId() : null)
                .build();
    }

    public List<BranchResponse> list() {
        List<Branch> branches = branchRepository.findByStatus("ACTIVE");

        return branches.stream()
                .map(branch -> BranchResponse.builder()
                        .id(branch.getId())
                        .branchName(branch.getBranchName())
                        .location(branch.getLocation())
                        .status(branch.getStatus())
                        .parentId(branch.getParent() != null ? branch.getParent().getId() : null)
                        .build()
                )
                .toList();
    }

    public List<BranchResponse> listBranchesForUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow();

        if(user.getRoles().contains("SUPER_ADMIN")){
            return list(); // all branches
        }

        List<Branch> branches = userBranchRepository.findByUser(user).stream()
                .map(UserBranch::getBranch)
                .toList();

        return branches.stream().map(this::mapToDto).toList();
    }

    public List<BranchTreeResponse> getTree() {
        // simplified - build hierarchy
        return BranchTreeBuilder.build(branchRepository.findAll());
    }
}

